package com.MiniProject.eventregistration.controller;

import com.MiniProject.eventregistration.DTOs.RegisterRequestDTO;
import com.MiniProject.eventregistration.DTOs.UserResponseDTO;
import com.MiniProject.eventregistration.Service.JwtService;
import com.MiniProject.eventregistration.Service.service;
import com.MiniProject.eventregistration.entity.LoginDTO;
import com.MiniProject.eventregistration.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private service userService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody RegisterRequestDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(loginDTO.getEmail());
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}