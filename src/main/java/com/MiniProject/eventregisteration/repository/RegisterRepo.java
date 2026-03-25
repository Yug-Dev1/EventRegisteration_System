package com.MiniProject.eventregisteration.repository;

import jakarta.servlet.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepo extends JpaRepository<Registration,Long> {
}
