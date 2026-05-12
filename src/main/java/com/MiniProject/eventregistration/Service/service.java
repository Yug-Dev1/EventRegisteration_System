package com.MiniProject.eventregistration.Service;

import com.MiniProject.eventregistration.DTOs.RegisterRequestDTO;
import com.MiniProject.eventregistration.DTOs.UserResponseDTO;
import com.MiniProject.eventregistration.entity.Role;
import com.MiniProject.eventregistration.entity.User;
import com.MiniProject.eventregistration.exception.ResourceNotFound;
import com.MiniProject.eventregistration.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class service {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDTO getUser(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles()
        );
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public UserResponseDTO register(RegisterRequestDTO dto) {

        User user = new User();

        user.setName(dto.getName());
        user.setAge(dto.getAge());
        user.setEmail(dto.getEmail());

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        if (dto.getRoles() == null || dto.getRoles().isEmpty()) {
            user.setRoles(List.of(Role.ROLE_USER));
        } else {
            user.setRoles(dto.getRoles());
        }

        User saved = userRepo.save(user);

        return new UserResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getRoles()
        );
    }
}