package com.MiniProject.eventregistration.repository;

import com.MiniProject.eventregistration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {


}
