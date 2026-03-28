package com.MiniProject.eventregistration.Service;

import com.MiniProject.eventregistration.DTOs.UserResponseDTO;
import com.MiniProject.eventregistration.entity.User;
import com.MiniProject.eventregistration.exception.ResourceNotFound;
import com.MiniProject.eventregistration.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // makes SpringBean
public class service {
    @Autowired  //Helps in Dependency Injection
    private UserRepo userRepo;

    public User createUser(User user){
        return userRepo.save(user);
    }

    public UserResponseDTO getUser(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
