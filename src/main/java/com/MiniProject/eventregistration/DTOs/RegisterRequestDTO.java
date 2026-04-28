package com.MiniProject.eventregistration.DTOs;

import com.MiniProject.eventregistration.entity.Role;
import jakarta.validation.constraints.*;

import java.util.List;

public class RegisterRequestDTO {

    @NotBlank
    private String name;

    @Min(1)
    private int age;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private List<Role> roles;

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public List<Role> getRoles() { return roles; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRoles(List<Role> roles) { this.roles = roles; }
}