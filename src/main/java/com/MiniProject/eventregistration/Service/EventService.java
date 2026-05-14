package com.MiniProject.eventregistration.Service;

import com.MiniProject.eventregistration.DTOs.EventCreateDTO;
import com.MiniProject.eventregistration.DTOs.EventResponseDTO;
import com.MiniProject.eventregistration.entity.Event;
import com.MiniProject.eventregistration.exception.ResourceNotFound;
import com.MiniProject.eventregistration.mongo.document.EventPageConfig;
import com.MiniProject.eventregistration.mongo.repository_mongo.EventPageConfigRepository;
import com.MiniProject.eventregistration.repository.EventRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private EventRepo eventRepo;
    private EventPageConfigRepository eventPageConfigRepository;
    EventService(EventRepo eventRepo, EventPageConfigRepository eventPageConfigRepository){
        this.eventRepo=eventRepo;
        this.eventPageConfigRepository=eventPageConfigRepository;
    }

    public EventResponseDTO getEvent(Long id) {

        Event event = eventRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Event not found"));

        EventPageConfig pageConfig = eventPageConfigRepository.findByEventId(id)
                .orElseThrow(() -> new ResourceNotFound("Event page config not found"));

        return EventResponseDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .location(event.getLocation())
                .date(event.getDate())
                .minAge(event.getMinAge())
                .maxAge(event.getMaxAge())
                .availableSeats(event.getAvailableSeats())
                .pageConfig(pageConfig)
                .build();
    }

    public List<Event> getAllEvents(){
        return eventRepo.findAll();
    }

    @Transactional
    public EventResponseDTO updateEvent(Long id, EventResponseDTO dto) {

        Event event = eventRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Event not found"));

        EventPageConfig config = eventPageConfigRepository.findByEventId(id)
                .orElseThrow(() -> new ResourceNotFound("Event page config not found"));

        // ---------------- SQL UPDATE ----------------

        if (dto.getTitle() != null)
            event.setTitle(dto.getTitle());

        if (dto.getDescription() != null)
            event.setDescription(dto.getDescription());

        if (dto.getLocation() != null)
            event.setLocation(dto.getLocation());

        if (dto.getDate() != null)
            event.setDate(dto.getDate());

        if (dto.getMinAge() != null)
            event.setMinAge(dto.getMinAge());

        if (dto.getMaxAge() != null)
            event.setMaxAge(dto.getMaxAge());

        if (dto.getMaxSeats() != null) {
            int bookedSeats = event.getMaxSeats() - event.getAvailableSeats();

            event.setMaxSeats(dto.getMaxSeats());
            event.setAvailableSeats(dto.getMaxSeats() - bookedSeats);
        }

        // ---------------- MONGO UPDATE ----------------

        if (dto.getPageConfig() != null) {

            EventPageConfig incoming = dto.getPageConfig();

            if (incoming.getEventType() != null)
                config.setEventType(incoming.getEventType());

            if (incoming.getMedia() != null)
                config.setMedia(incoming.getMedia());

            if (incoming.getTheme() != null)
                config.setTheme(incoming.getTheme());

            if (incoming.getSchedule() != null)
                config.setSchedule(incoming.getSchedule());

            if (incoming.getParticipants() != null)
                config.setParticipants(incoming.getParticipants());

            if (incoming.getFaq() != null)
                config.setFaq(incoming.getFaq());

            if (incoming.getTicketTiers() != null)
                config.setTicketTiers(incoming.getTicketTiers());

            if (incoming.getCustomAttributes() != null)
                config.setCustomAttributes(incoming.getCustomAttributes());

            if (incoming.getCustomFormFields() != null)
                config.setCustomFormFields(incoming.getCustomFormFields());
        }

        eventRepo.save(event);
        eventPageConfigRepository.save(config);

        return EventResponseDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .location(event.getLocation())
                .date(event.getDate())
                .minAge(event.getMinAge())
                .maxAge(event.getMaxAge())
                .maxSeats(event.getMaxSeats())
                .availableSeats(event.getAvailableSeats())
                .pageConfig(config)
                .build();
    }

    public Event createEvent(EventCreateDTO dto) {

        Event event = new Event();

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        event.setDate(dto.getDate());
        event.setMinAge(dto.getMinAge());
        event.setMaxAge(dto.getMaxAge());
        event.setMaxSeats(dto.getMaxSeats());
        event.setAvailableSeats(dto.getMaxSeats());

        Event savedEvent = eventRepo.save(event);

        EventPageConfig config = dto.getPageConfig();

        if (config == null) {
            config = EventPageConfig.builder()
                    .eventType("GENERAL")
                    .build();
        }

        config.setEventId(savedEvent.getId());

        eventPageConfigRepository.save(config);

        return savedEvent;
    }
}
