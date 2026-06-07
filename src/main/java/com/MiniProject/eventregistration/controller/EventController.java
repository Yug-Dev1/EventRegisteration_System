package com.MiniProject.eventregistration.controller;

import com.MiniProject.eventregistration.DTOs.EventCreateDTO;
import com.MiniProject.eventregistration.DTOs.EventResponseDTO;
import com.MiniProject.eventregistration.Service.EventService;
import com.MiniProject.eventregistration.entity.Event;
import com.MiniProject.eventregistration.repository.EventRepo;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final EventRepo eventRepo;

    public EventController(EventService eventService, EventRepo eventRepo) {
        this.eventService = eventService;
        this.eventRepo = eventRepo;
    }

    @PostMapping
    public Event createEvent(@Valid @RequestBody EventCreateDTO dto) {
        return eventService.createEvent(dto);
    }

    @GetMapping
    public ResponseEntity<Page<Event>> getAllEvents(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "15")int size) {
        return ResponseEntity.ok(
                eventService.getAllEvents(page, size)
        );
    }

    @GetMapping("/{id}")
    public EventResponseDTO getEvent(@PathVariable Long id){
        return eventService.getEvent(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EventResponseDTO> updateEvent(
            @PathVariable Long id,
            @RequestBody EventResponseDTO dto
    ) {
        return ResponseEntity.ok(eventService.updateEvent(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {

        return ResponseEntity.ok(eventService.deleteEvent(id));

    }
}
