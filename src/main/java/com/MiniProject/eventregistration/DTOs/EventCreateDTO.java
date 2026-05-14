package com.MiniProject.eventregistration.DTOs;

import com.MiniProject.eventregistration.mongo.document.EventPageConfig;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventCreateDTO {

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotBlank(message = "Location cannot be empty")
    private String location;

    @Future(message = "Date must be in future")
    private LocalDate date;

    @Min(value = 0, message = "Minimum age cannot be negative")
    private int minAge;

    @Min(value = 0, message = "Maximum age cannot be negative")
    private int maxAge;

    @Min(value = 1, message = "Seats must be at least 1")
    private int maxSeats;

    @Valid
    private EventPageConfig pageConfig;
}