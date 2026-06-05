package com.MiniProject.eventregistration.controller;

import com.MiniProject.eventregistration.Service.service;
import com.MiniProject.eventregistration.entity.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final service userService;

    public UserController(service userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUser(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "15")int size){
        return ResponseEntity.ok(
            userService.getAllUsers(page, size)
        );
    }
}

