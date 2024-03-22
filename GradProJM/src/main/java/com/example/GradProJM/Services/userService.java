package com.example.GradProJM.Services;

import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.User;
import com.example.GradProJM.Repos.CustomerRepository;
import com.example.GradProJM.Repos.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class userService {
    private final userRepository userRepo;

    @Autowired
    public userService(userRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getUsers(){
        return userRepo.findAll();
    }

    public void addNewUser(User user) {
        Optional<User> findByuserEmail=userRepo.findByuserEmail(user.getUserEmail());
        if(findByuserEmail.isPresent()){
            throw new IllegalStateException("Email Taken");
        }
        else {
            userRepo.saveAndFlush(user);
        }
    }

    public User getUserbyId(int userid) {
        Optional<User> userOptional=userRepo.findByuserid(userid);
        return userOptional.orElse(null);    }
}
