package com.MiniProject.eventregistration.Service;

import com.MiniProject.eventregistration.DTOs.EventCreateDTO;
import com.MiniProject.eventregistration.DTOs.EventResponseDTO;
import com.MiniProject.eventregistration.entity.Enums.EventType;
import com.MiniProject.eventregistration.entity.Event;
import com.MiniProject.eventregistration.exception.ResourceNotFound;
import com.MiniProject.eventregistration.mongo.document.EventPageConfig;
import com.MiniProject.eventregistration.mongo.repository_mongo.EventPageConfigRepository;
import com.MiniProject.eventregistration.repository.EventRepo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

    public Page<Event> getAllEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventRepo.findAll(pageable);
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

        boolean mongoUpdated = false;
        EventPageConfig oldConfig = EventPageConfig.builder()
                .id(config.getId())
                .eventId(config.getEventId())
                .eventType(config.getEventType())
                .media(config.getMedia())
                .theme(config.getTheme())
                .schedule(config.getSchedule())
                .participants(config.getParticipants())
                .faq(config.getFaq())
                .ticketTiers(config.getTicketTiers())
                .customAttributes(config.getCustomAttributes())
                .customFormFields(config.getCustomFormFields())
                .build();
        try {
            eventRepo.save(event);
            eventPageConfigRepository.save(config);
            mongoUpdated = true;
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
        }catch(Exception ex){
            if (mongoUpdated) {
                try {
                    eventPageConfigRepository.save(oldConfig);
                } catch (Exception ignored) {
                }
            }
            throw new RuntimeException("Updation failed", ex);
        }
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
                    .eventType(EventType.GENERAL)
                    .build();
        }

        config.setEventId(savedEvent.getId());

        eventPageConfigRepository.save(config);

        return savedEvent;
    }

    @Transactional
    public String deleteEvent(Long id) {

        Event event = eventRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Event Doesn't Exists"));

        EventPageConfig configBackup = eventPageConfigRepository.findByEventId(id) .orElseThrow(() -> new ResourceNotFound("Event Page Doesn't Exists"));

        eventPageConfigRepository.deleteByEventId(id);

        try {
            eventRepo.delete(event);
        } catch (Exception e) {
            if (configBackup != null) {
                eventPageConfigRepository.save(configBackup);
            }
            throw new RuntimeException("Delete failed, changes rolled back: " + e.getMessage());
        }

        return "Event deleted successfully";
    }
}
