package com.MiniProject.eventregistration.repository;

import com.MiniProject.eventregistration.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.domain.Specification;

public interface EventRepo extends JpaRepository<Event,Long>, JpaSpecificationExecutor<Event> {
}
