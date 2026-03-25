package com.MiniProject.eventregisteration.repository;

import com.MiniProject.eventregisteration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
