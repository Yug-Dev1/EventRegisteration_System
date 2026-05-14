package com.MiniProject.eventregistration.DTOs;

import java.util.List;
import com.MiniProject.eventregistration.entity.Enums.Role;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private List<Role> roles;

    public UserResponseDTO(Long id, String name, String email, List<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<Role> getRoles() { return roles; }
}