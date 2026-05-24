package com.MiniProject.eventregistration.Service;

import com.MiniProject.eventregistration.DTOs.RegistrationRequestDTO;
import com.MiniProject.eventregistration.DTOs.RegistrationResponseDTO;
import com.MiniProject.eventregistration.entity.*;
import com.MiniProject.eventregistration.entity.Enums.PaymentStatus;
import com.MiniProject.eventregistration.entity.Enums.RegistrationStatus;
import com.MiniProject.eventregistration.exception.ResourceNotFound;
import com.MiniProject.eventregistration.mongo.document.EventPageConfig;
import com.MiniProject.eventregistration.mongo.document.RegistrationAnswers;
import com.MiniProject.eventregistration.mongo.repository_mongo.EventPageConfigRepository;
import com.MiniProject.eventregistration.mongo.repository_mongo.RegistrationAnswerRepository;
import com.MiniProject.eventregistration.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class RegistrationService {

    private final UserRepo userRepo;
    private final EventRepo eventRepo;
    private final RegisterRepo registerRepo;
    private final EventPageConfigRepository eventPageConfigRepository;
    private final RegistrationAnswerRepository registrationAnswerRepository;

    public RegistrationService(UserRepo userRepo, EventRepo eventRepo, RegisterRepo registerRepo,EventPageConfigRepository eventPageConfigRepository,RegistrationAnswerRepository registrationAnswerRepository) {
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
        this.registerRepo = registerRepo;
        this.eventPageConfigRepository=eventPageConfigRepository;
        this.registrationAnswerRepository=registrationAnswerRepository;
    }

    public RegistrationResponseDTO registerUser(RegistrationRequestDTO dto) {
        String email= SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user=userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFound("User Doesn't Exist"));
        Event event=eventRepo.findById(dto.getEventId()).orElseThrow(()-> new ResourceNotFound("Event Doesn't Exist"));

        EventPageConfig eventPageConfig= eventPageConfigRepository.findByEventId(dto.getEventId()).orElseThrow(()-> new ResourceNotFound("Event Config Doesn't Exist"));

        // Event expired
        if (event.getDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Event already completed");
        }
        // Age validation
        if (user.getAge() < event.getMinAge() || user.getAge() > event.getMaxAge()) {
            throw new RuntimeException("User does not meet age requirements");
        }
        // Multiple ticket rule
        if ((event.getAllowMultipleTickets()!=null && !event.getAllowMultipleTickets()) && dto.getTicketCount() > 1) {
            throw new RuntimeException("Only one ticket allowed for this event");
        }
        // Seat validation
        if (event.getAvailableSeats() < dto.getTicketCount()) {
            throw new RuntimeException("Not enough seats available");
        }

        EventPageConfig.TicketTier selectedTier=eventPageConfig.getTicketTiers()
                .stream()//Converts the List into a Stream so you can use functional operations on it
                .filter(t -> t.getName().equalsIgnoreCase(dto.getTicketTierName()))//loops through each tier
                .findFirst()
                .orElseThrow(()-> new ResourceNotFound("Ticket Tier DOESN'T Exist"));

        if(selectedTier.getQuantity() < dto.getTicketCount()){
                throw new RuntimeException("Not enough tickets");
        }

        if(eventPageConfig.getCustomFormFields()!=null){
            for(EventPageConfig.FormField formField : eventPageConfig.getCustomFormFields()){
                if(formField.isRequired()){
                    if(dto.getAnswers() == null || !dto.getAnswers().containsKey(formField.getLabel()) )
                    {
                        throw new RuntimeException(
                                "Missing required field: " + formField.getLabel()
                        );
                    }
                }
            }
        }

        Double totalAmt=dto.getTicketCount()*selectedTier.getPrice();

        //Seat logic for SQL
        event.setAvailableSeats(event.getAvailableSeats() - dto.getTicketCount());
        eventRepo.save(event);

        //Seat logic for MongoDB
        selectedTier.setQuantity(selectedTier.getQuantity() - dto.getTicketCount());
        eventPageConfigRepository.save(eventPageConfig);

        // Save registration
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

        Registration savedRegistration = registerRepo.save(registration);
        // Save answers in Mongo
        if (dto.getAnswers() != null && !dto.getAnswers().isEmpty()) {
            RegistrationAnswers answers = RegistrationAnswers.builder()
                    .registrationId(savedRegistration.getId())
                    .userId(user.getId())
                    .eventId(event.getId())
                    .answers(dto.getAnswers())
                    .build();

            registrationAnswerRepository.save(answers);

        }

        // Response
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
    public void cancelRegistration(Long eventID){
        String email=SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user=userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFound("user doesn't EXIST"));
        Event event= eventRepo.findById(eventID).orElseThrow(()-> new ResourceNotFound("event doesn't EXIST"));
        Registration registration = registerRepo.findByUserAndEvent(user, event)
                .orElseThrow(() -> new ResourceNotFound("Registration not found"));

        if(registration.getStatus()==RegistrationStatus.CANCELLED) {
            throw new RuntimeException("Already CANCELLED");
        }

       event.setAvailableSeats(event.getAvailableSeats()+registration.getTicketCount());
       EventPageConfig eventPageConfig=eventPageConfigRepository.findByEventId(eventID).orElseThrow(()-> new ResourceNotFound("Does now exist"));

       EventPageConfig.TicketTier tier=eventPageConfig.getTicketTiers()
                       .stream().filter(t->t.getName().equalsIgnoreCase(registration.getTicketTierName()))
                       .findFirst()
                        .orElseThrow(()->new ResourceNotFound("Tier doesn't exists"));

       tier.setQuantity(tier.getQuantity()+registration.getTicketCount());
        registration.setStatus(RegistrationStatus.CANCELLED);

        eventRepo.save(event);

        eventPageConfigRepository.save(eventPageConfig);

        registerRepo.save(registration);
    }
}

// For Validation

// Spring Security stores authenticated user info for current request thread.
// SecurityContextHolder -> current security container
// getContext() -> current request's security context
// getAuthentication() -> authenticated user token object set by JwtFilter
// getName() -> principal name (in our case JWT subject = user email)
// Used so client never sends userId manually; backend derives user securely from JWT.

/* --------------------------------------*/

/* Never trust frontend input.*/
// For fields like age, no. of tickets that could be validated at the start

// Frontend validation improves UX only.
// Backend validation enforces actual business/security rules.
//
// Example:
// Frontend may restrict ticketCount to max 5 via UI,
// but attacker can bypass frontend and directly call API via Postman:
//
// {
//   "ticketCount": 999
// }
//
// Therefore backend must ALWAYS validate:
// age eligibility
// seat availability
// ticket tier existence
// required form fields
