package com.MiniProject.eventregistration.Service;

import com.MiniProject.eventregistration.entity.Event;
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

    public Event getEvent(Long id){
        return eventRepo.findById(id).orElseThrow(()->new RuntimeException("Event Not found"));
    }

    public List<Event> getAllEvents(){
        return eventRepo.findAll();
    }
}
