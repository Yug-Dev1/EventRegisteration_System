package com.MiniProject.eventregistration.controller;

import com.MiniProject.eventregistration.Service.service;
import com.MiniProject.eventregistration.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final service userService;

    public UserController(service userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }
}
