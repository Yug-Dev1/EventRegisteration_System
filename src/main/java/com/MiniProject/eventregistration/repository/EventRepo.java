package com.MiniProject.eventregistration.repository;

import com.MiniProject.eventregistration.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event,Long> {
}
