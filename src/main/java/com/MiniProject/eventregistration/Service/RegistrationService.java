package com.MiniProject.eventregistration.Service;

import com.MiniProject.eventregistration.entity.*;
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
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // 1. Event already happened
        if (event.getDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Event already happened");
        }

        // 2. Seats check
        if (event.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available");
        }

        // 3. Age validation
        if (user.getAge() < event.getMinAge() || user.getAge() > event.getMaxAge()) {
            throw new RuntimeException("Age limit violation");
        }

        // 4. Duplicate check
        if (registerRepo.existsByUserAndEvent(user, event)) {
            throw new RuntimeException("User already registered");
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
}