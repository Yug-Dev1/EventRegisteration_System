package com.MiniProject.eventregistration.controller;

import com.MiniProject.eventregistration.DTOs.BookingResponseDTO;
import com.MiniProject.eventregistration.DTOs.RegistrationRequestDTO;
import com.MiniProject.eventregistration.DTOs.RegistrationResponseDTO;
import com.MiniProject.eventregistration.entity.Registration;
import com.MiniProject.eventregistration.Service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    @DeleteMapping("/cancel/{eventId}")
    public String cancelRegistration(@PathVariable Long eventId) {
        registrationService.cancelRegistration(eventId);
        return "Registration cancelled successfully";
    }

    @GetMapping("/my")
    public ResponseEntity<Page<BookingResponseDTO>> MyBookingHistory(@PageableDefault(size=10,sort="registrationDate",direction= Sort.Direction.DESC)Pageable pageable){
        return ResponseEntity.ok(
                registrationService.getMyBookings(pageable)
        );
    }
}