package com.MiniProject.eventregistration.controller;

import com.MiniProject.eventregistration.DTOs.EventResponseDTO;
import com.MiniProject.eventregistration.Service.EventService;
import com.MiniProject.eventregistration.entity.Event;
import com.MiniProject.eventregistration.mongo.service_mongo.EventPageConfigService;
import com.MiniProject.eventregistration.repository.EventRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Event createEvent(@Valid @RequestBody Event event){
        event.setAvailableSeats(event.getMaxSeats());
        Event savedEvent = eventRepo.save(event);
        eventPageConfigService.createDefaultConfig(savedEvent.getId());
        return savedEvent;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public EventResponseDTO getEvent(@PathVariable Long id){
        return eventService.getEvent(id);
    }
}
