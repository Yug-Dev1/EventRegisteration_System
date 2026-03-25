package com.MiniProject.eventregistration.repository;

import com.MiniProject.eventregistration.entity.Event;
import com.MiniProject.eventregistration.entity.User;
import com.MiniProject.eventregistration.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepo extends JpaRepository<Registration, Long> {
    boolean existsByUserAndEvent(User user, Event event);
}