package com.MiniProject.eventregisteration.repository;

import com.MiniProject.eventregisteration.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event,Long> {
}
