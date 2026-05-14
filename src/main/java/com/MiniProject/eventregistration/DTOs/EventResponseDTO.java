package com.MiniProject.eventregistration.DTOs;

import com.MiniProject.eventregistration.mongo.document.EventPageConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDTO {

    private Long id;

    private String title;
    private String description;
    private String location;
    private LocalDate date;

    private Integer minAge;
    private Integer maxAge;
    private Integer maxSeats;
    private Integer availableSeats;

    private EventPageConfig pageConfig;
}