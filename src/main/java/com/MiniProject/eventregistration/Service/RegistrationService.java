package com.MiniProject.eventregistration.Service;

import com.MiniProject.eventregistration.entity.*;
import com.MiniProject.eventregistration.exception.ResourceNotFound;
import com.MiniProject.eventregistration.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class RegistrationService {

    private final UserRepo userRepo;
    private final EventRepo eventRepo;
    private final RegisterRepo registerRepo;

    public RegistrationService(UserRepo userRepo, EventRepo eventRepo, RegisterRepo registerRepo) {
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
        this.registerRepo = registerRepo;
    }

    public Registration registerUser(Long userId, Long eventId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() ->new ResourceNotFound("Event not found"));

        // 1. Event already happened
        if (event.getDate().isBefore(LocalDate.now())) {
            throw new ResourceNotFound("Event Already happened ");
        }

        // 2. Seats check
        if (event.getAvailableSeats() <= 0) {
            throw new ResourceNotFound("No Seats Available");
        }

        // 3. Age validation
        if (user.getAge() < event.getMinAge() || user.getAge() > event.getMaxAge()) {
            throw new ResourceNotFound("Age Limit Violation");
        }

        // 4. Duplicate check
        if (registerRepo.existsByUserAndEvent(user, event)) {
            throw new ResourceNotFound("User Already Registered");
        }

        // 5. Create registration
        Registration registration = new Registration();
        registration.setUser(user);
        registration.setEvent(event);
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setStatus(RegistrationStatus.ACTIVE);

        // 6. Decrease seats
        event.setAvailableSeats(event.getAvailableSeats() - 1);

        // 7. Save
        return registerRepo.save(registration);
    }

    public void cancelRegistration(Long userID,Long eventID){
        User user=userRepo.findById(userID).orElseThrow(()->new ResourceNotFound("user doesn't EXIST"));
        Event event= eventRepo.findById(eventID).orElseThrow(()-> new ResourceNotFound("event doesn't EXIST"));
        Registration registration = registerRepo.findByUserAndEvent(user, event)
                .orElseThrow(() -> new ResourceNotFound("Registration not found"));

        if(registration.getStatus()==RegistrationStatus.CANCELLED){
            throw new RuntimeException("Already CANCELLED");
        }
        registration.setStatus(RegistrationStatus.CANCELLED);
       event.setAvailableSeats(event.getAvailableSeats()+1);
       registerRepo.save(registration);
    }
}