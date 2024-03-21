package com.example.GradProJM.Controller;


import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.GradProJM.Services.*;

@RestController
@RequestMapping(path="user")
public class UserController {

    private final userService userService;
    private final customerService custService;

    @Autowired
    public UserController(userService userService, customerService custService) {
        this.userService = userService;
        this.custService = custService;
    }


    @GetMapping
    public List<User> std(){
        return userService.getUsers();
    }

    @PostMapping
    public void registerNewcustomer(@RequestBody User user, Customer customer){
        userService.addNewUser(user);
        customerService.addNewCustomer(customer);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int userid){
         User user=userService.getUserbyId(userid);
         return user;
    }

}
