package com.MiniProject.eventregistration.DTOs;

import java.time.LocalDate;

public class EventResponseDTO {

    private Long id;
    private String title;
    private String location;
    private LocalDate date;
    private int availableSeats;

    public EventResponseDTO(Long id, String title, String location, LocalDate date, int availableSeats) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.date = date;
        this.availableSeats = availableSeats;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public LocalDate getDate() { return date; }
    public int getAvailableSeats() { return availableSeats; }
}