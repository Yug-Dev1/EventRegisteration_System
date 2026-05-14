package com.MiniProject.eventregistration.controller;

import com.MiniProject.eventregistration.DTOs.EventCreateDTO;
import com.MiniProject.eventregistration.DTOs.EventResponseDTO;
import com.MiniProject.eventregistration.Service.EventService;
import com.MiniProject.eventregistration.entity.Event;
import com.MiniProject.eventregistration.mongo.service_mongo.EventPageConfigService;
import com.MiniProject.eventregistration.repository.EventRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final EventPageConfigService eventPageConfigService;
    private final EventRepo eventRepo;

    public EventController(EventService eventService, EventPageConfigService eventPageConfigService, EventRepo eventRepo) {
        this.eventService = eventService;
        this.eventPageConfigService = eventPageConfigService;
        this.eventRepo = eventRepo;
    }

    @PostMapping
    public Event createEvent(@Valid @RequestBody EventCreateDTO dto) {
        return eventService.createEvent(dto);
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
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
