package com.MiniProject.eventregistration.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Password cannot be empty")
    private String password;
    @Min(value = 1, message = "Age must be positive")
    private int age;
    @Email(message = "Invalid email format")
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    /*EAGER fetch loads all associated data at the time of the initial query,
     whereas LAZY fetch delays loading of related data until it is explicitly accessed.
     Thus, we use eagr as spring security also need role at login*/
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    //without this is would store like 0,1,2
    private List<Role> roles;


}