package com.MiniProject.eventregistration.controller;

import com.MiniProject.eventregistration.entity.Registration;
import com.MiniProject.eventregistration.Service.RegistrationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public Registration registerUser(
            @RequestParam Long userId,
            @RequestParam Long eventId
    ) {
        return registrationService.registerUser(userId, eventId);
    }

    @DeleteMapping
    public String cancelRegistration(
            @RequestParam Long userId,
            @RequestParam Long eventId
    ) {
        registrationService.cancelRegistration(userId, eventId);
        return "Registration cancelled successfully";
    }
}