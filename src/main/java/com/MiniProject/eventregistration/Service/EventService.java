package com.MiniProject.eventregistration.Service;

import com.MiniProject.eventregistration.DTOs.EventResponseDTO;
import com.MiniProject.eventregistration.entity.Event;
import com.MiniProject.eventregistration.exception.ResourceNotFound;
import com.MiniProject.eventregistration.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

   /* @Autowired
    private EventRepo eventRepo;

    This is Field injection, works but not prefered,
    we will use the constructor injection   */

    private EventRepo eventRepo;

    EventService(EventRepo eventRepo){
        this.eventRepo=eventRepo;
    }

    public Event createEvent(Event event){
        event.setAvailableSeats(event.getMaxSeats());
        return eventRepo.save(event);
    }

    public EventResponseDTO getEvent(Long id) {
        Event event = eventRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Event not found"));

        return new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getLocation(),
                event.getDate(),
                event.getAvailableSeats()
        );
    }

    public List<Event> getAllEvents(){
        return eventRepo.findAll();
    }
}
