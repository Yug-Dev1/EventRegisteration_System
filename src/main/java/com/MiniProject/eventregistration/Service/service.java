package com.MiniProject.eventregistration.Service;

import com.MiniProject.eventregistration.entity.User;
import com.MiniProject.eventregistration.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // makes SpringBean
public class service {
    @Autowired  //Helps in Dependency Injection
    private UserRepo userRepo;

    public User createUser(User user){
        return userRepo.save(user);
    }

    public User getUser(Long id){
        return userRepo.findById(id).orElseThrow(()->new RuntimeException("User Not Found"));
    }

}
