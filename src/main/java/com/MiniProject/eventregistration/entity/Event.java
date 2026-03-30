package com.MiniProject.eventregistration.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title cannot be empty")
    private String title;
    private String description;
    private String location;
    @Future(message = "Date must be in future")
    private LocalDate date;

    private int minAge;
    private int maxAge;
    @Min(value = 1, message = "Seats must be at least 1")
    private int maxSeats;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer availableSeats;
}