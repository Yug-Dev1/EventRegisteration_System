package com.MiniProject.eventregistration.controller;

import com.MiniProject.eventregistration.DTOs.RegistrationRequestDTO;
import com.MiniProject.eventregistration.DTOs.RegistrationResponseDTO;
import com.MiniProject.eventregistration.entity.Registration;
import com.MiniProject.eventregistration.Service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public RegistrationResponseDTO registerForEvent(
            @Valid @RequestBody RegistrationRequestDTO dto
    ) {
        return registrationService.registerUser(dto);
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