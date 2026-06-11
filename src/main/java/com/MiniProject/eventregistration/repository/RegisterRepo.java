package com.MiniProject.eventregistration.repository;

import com.MiniProject.eventregistration.entity.Event;
import com.MiniProject.eventregistration.entity.User;
import com.MiniProject.eventregistration.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface RegisterRepo extends JpaRepository<Registration, Long> {
    boolean existsByUserAndEvent(User user, Event event);

    Optional<Registration> findByUserAndEvent(User user, Event event);

    Page<Registration> findByUserId(Long userId, Pageable pageable);
}