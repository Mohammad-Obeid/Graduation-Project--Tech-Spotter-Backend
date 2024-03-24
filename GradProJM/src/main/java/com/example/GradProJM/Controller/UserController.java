package com.example.GradProJM.Controller;


import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.ShopOwner;
import com.example.GradProJM.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import com.example.GradProJM.Services.*;

@RestController
@RequestMapping(path="user")
public class UserController {
    private final userService userService;
    private final customerService custService;
    private static String code = "";
    private static User user1 = new User();
    private static LocalDateTime loctime;
    @Autowired
    public UserController(userService userService, customerService custService) {
        this.userService = userService;
        this.custService = custService;
    }
    @GetMapping
    public List<User> std() {
        return userService.getUsers();
    }



    @PostMapping("addnewuser")
    public ResponseEntity<Void> addNewUser(@RequestBody User user) {
        System.out.println("User Object : " + user.toString());
        user1 = user;

        userService.SendEmailVerification(user);
        loctime = LocalDateTime.now().plusMinutes(10);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int userid) {
        User user = userService.getUserbyId(userid);
        return user;
    }

}
