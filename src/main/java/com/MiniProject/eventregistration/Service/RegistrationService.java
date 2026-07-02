package com.MiniProject.eventregistration.Service;

import com.MiniProject.eventregistration.DTOs.BookingResponseDTO;
import com.MiniProject.eventregistration.DTOs.RegistrationFilterDTO;
import com.MiniProject.eventregistration.DTOs.RegistrationRequestDTO;
import com.MiniProject.eventregistration.DTOs.RegistrationResponseDTO;
import com.MiniProject.eventregistration.Service.specification.RegistrationSpecification;
import com.MiniProject.eventregistration.entity.*;
import com.MiniProject.eventregistration.entity.Enums.PaymentStatus;
import com.MiniProject.eventregistration.entity.Enums.RegistrationStatus;
import com.MiniProject.eventregistration.exception.ResourceNotFound;
import com.MiniProject.eventregistration.mongo.document.EventPageConfig;
import com.MiniProject.eventregistration.mongo.document.RegistrationAnswers;
import com.MiniProject.eventregistration.mongo.repository_mongo.EventPageConfigRepository;
import com.MiniProject.eventregistration.mongo.repository_mongo.RegistrationAnswerRepository;
import com.MiniProject.eventregistration.repository.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class RegistrationService {

    private final UserRepo userRepo;
    private final EventRepo eventRepo;
    private final RegisterRepo registerRepo;
    private final EventPageConfigRepository eventPageConfigRepository;
    private final RegistrationAnswerRepository registrationAnswerRepository;

    public RegistrationService(
            UserRepo userRepo,
            EventRepo eventRepo,
            RegisterRepo registerRepo,
            EventPageConfigRepository eventPageConfigRepository,
            RegistrationAnswerRepository registrationAnswerRepository
    ) {
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
        this.registerRepo = registerRepo;
        this.eventPageConfigRepository = eventPageConfigRepository;
        this.registrationAnswerRepository = registrationAnswerRepository;
    }

    @Transactional
    public RegistrationResponseDTO registerUser(RegistrationRequestDTO dto) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("User doesn't exist"));

        Event event = eventRepo.findById(dto.getEventId())
                .orElseThrow(() -> new ResourceNotFound("Event doesn't exist"));

        EventPageConfig eventPageConfig = eventPageConfigRepository.findByEventId(dto.getEventId())
                .orElseThrow(() -> new ResourceNotFound("Event config doesn't exist"));

        if (event.getDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Event already completed");
        }

        if (user.getAge() < event.getMinAge() || user.getAge() > event.getMaxAge()) {
            throw new RuntimeException("User does not meet age requirements");
        }

        if ((event.getAllowMultipleTickets() != null
                && !event.getAllowMultipleTickets())
                && dto.getTicketCount() > 1) {
            throw new RuntimeException("Only one ticket allowed");
        }

        if (event.getAvailableSeats() < dto.getTicketCount()) {
            throw new RuntimeException("Not enough seats available");
        }

        EventPageConfig.TicketTier selectedTier = eventPageConfig.getTicketTiers()
                .stream()
                .filter(t -> t.getName().equalsIgnoreCase(dto.getTicketTierName()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFound("Ticket tier doesn't exist"));

        if (selectedTier.getQuantity() < dto.getTicketCount()) {
            throw new RuntimeException("Not enough tickets");
        }

        if (eventPageConfig.getCustomFormFields() != null) {
            for (EventPageConfig.FormField formField : eventPageConfig.getCustomFormFields()) {
                if (formField.isRequired()) {
                    if (dto.getAnswers() == null
                            || !dto.getAnswers().containsKey(formField.getLabel())) {
                        throw new RuntimeException(
                                "Missing required field: " + formField.getLabel()
                        );
                    }
                }
            }
        }

        Double totalAmt = dto.getTicketCount() * selectedTier.getPrice();

        Registration savedRegistration = null;
        boolean mongoSeatUpdated = false;

        try {
            // SQL seat update
            event.setAvailableSeats(event.getAvailableSeats() - dto.getTicketCount());
            eventRepo.save(event);

            // Mongo seat update
            selectedTier.setQuantity(selectedTier.getQuantity() - dto.getTicketCount());
            eventPageConfigRepository.save(eventPageConfig);
            mongoSeatUpdated = true;

            // SQL registration
            Registration registration = Registration.builder()
                    .user(user)
                    .event(event)
                    .ticketTierName(selectedTier.getName())
                    .ticketCount(dto.getTicketCount())
                    .totalAmount(totalAmt)
                    .paymentStatus(PaymentStatus.SUCCESS)
                    .status(RegistrationStatus.ACTIVE)
                    .registrationDate(LocalDateTime.now())
                    .build();

            savedRegistration = registerRepo.save(registration);

            // Mongo answers
            if (dto.getAnswers() != null && !dto.getAnswers().isEmpty()) {
                RegistrationAnswers answers = RegistrationAnswers.builder()
                        .registrationId(savedRegistration.getId())
                        .userId(user.getId())
                        .eventId(event.getId())
                        .answers(dto.getAnswers())
                        .build();

                registrationAnswerRepository.save(answers);
            }

        } catch (Exception ex) {

            // compensate Mongo if already updated
            if (mongoSeatUpdated) {
                try {
                    selectedTier.setQuantity(selectedTier.getQuantity() + dto.getTicketCount());
                    eventPageConfigRepository.save(eventPageConfig);
                } catch (Exception ignored) {
                }
            }

            throw new RuntimeException("Registration failed", ex);
        }

        return RegistrationResponseDTO.builder()
                .registrationId(savedRegistration.getId())
                .eventTitle(event.getTitle())
                .ticketTierName(savedRegistration.getTicketTierName())
                .ticketCount(savedRegistration.getTicketCount())
                .totalAmount(savedRegistration.getTotalAmount())
                .paymentStatus(savedRegistration.getPaymentStatus())
                .status(savedRegistration.getStatus())
                .registrationDate(savedRegistration.getRegistrationDate())
                .answers(dto.getAnswers())
                .build();
    }

    @Transactional
    public void cancelRegistration(Long registrationId){
        String email=SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user=userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFound("user doesn't EXIST"));

        Registration registration =
                registerRepo.findById(registrationId)
                        .orElseThrow(()-> new RuntimeException("Registration doesn't exist"));
        if (!registration.getUser().getId().equals(user.getId())) {

            throw new RuntimeException("You cannot cancel another user's registration.");

        }
        Event event = registration.getEvent();

        if(registration.getStatus()==RegistrationStatus.CANCELLED) {
            throw new RuntimeException("Already CANCELLED");
        }

        event.setAvailableSeats(event.getAvailableSeats()+registration.getTicketCount());

        EventPageConfig eventPageConfig=eventPageConfigRepository.findByEventId(event.getId()).orElseThrow(()-> new ResourceNotFound("Does not exist"));

        EventPageConfig.TicketTier tier=eventPageConfig.getTicketTiers()
                .stream().filter(t->t.getName().equalsIgnoreCase(registration.getTicketTierName()))
                .findFirst()
                .orElseThrow(()->new ResourceNotFound("Tier doesn't exists"));

        int oldQuantity= tier.getQuantity();
        boolean mongoUpdated=false;
        try {
            tier.setQuantity(tier.getQuantity() + registration.getTicketCount());
            registration.setStatus(RegistrationStatus.CANCELLED);

            eventRepo.save(event);
            eventPageConfigRepository.save(eventPageConfig);
            mongoUpdated=true;
            registerRepo.save(registration);
        }catch (Exception ex) {
            // compensate mongo if already updated
            if (mongoUpdated) {
                try {
                    tier.setQuantity(oldQuantity);
                    eventPageConfigRepository.save(eventPageConfig);
                } catch (Exception ignored) {
                }
            }
            throw new RuntimeException("Cancellation failed", ex);
    }
}

    public Page<BookingResponseDTO> getMyBookings(
            RegistrationFilterDTO filter,
            Pageable pageable
    ){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = userRepo.findByEmail(authentication.getName())
                .orElseThrow(()->new RuntimeException("No user"));

        Specification<Registration> spec =
                RegistrationSpecification.belongsToUser(user.getId())
                        .and(
                                RegistrationSpecification.hasPaymentStatus(
                                        filter.getPaymentStatus()
                                )
                        )
                        .and(
                                RegistrationSpecification.hasTicketTier(
                                        filter.getTicketTierName()
                                )
                        )
                        .and(
                                RegistrationSpecification.registeredAfter(
                                        filter.getFromDate()
                                )
                        )
                        .and(
                                RegistrationSpecification.registeredBefore(
                                        filter.getToDate()
                                )
                        );
        Page<Registration> registrations =
                registerRepo.findAll(
                        spec,
                        pageable
                );

        return registrations.map(this::mapToDTO);
    }

    private BookingResponseDTO mapToDTO(
            Registration registration
    ) {
        return BookingResponseDTO.builder()
                .registrationId(registration.getId())
                .eventTitle(registration.getEvent().getTitle())
                .ticketTierName(registration.getTicketTierName())
                .ticketCount(registration.getTicketCount())
                .totalAmount(registration.getTotalAmount())
                .paymentStatus(registration.getPaymentStatus())
                .status(registration.getStatus())
                .registrationDate(
                        registration.getRegistrationDate()
                )
                .build();
    }

}
