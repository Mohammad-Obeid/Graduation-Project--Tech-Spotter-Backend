package com.example.GradProJM.Services;

import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.User;
import com.example.GradProJM.Repos.CustomerRepository;
import com.example.GradProJM.Repos.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class userService {
    private final userRepository userRepo;
    private static String g="";
    private static User user1=new User();

    @Autowired
    public userService(userRepository userRepo) {
        this.userRepo = userRepo;
    }


    @Autowired
    private EmailService emailService;

    private RandomCodeGenerator randomCode=new RandomCodeGenerator();

    public List<User> getUsers(){
        return userRepo.findAll();
    }

    public void SendEmailVerification(User user) {
        Optional<User> findByuserEmail=userRepo.findByuserEmail(user.getUserEmail());
        if(findByuserEmail.isPresent()){
            throw new IllegalStateException("Email Taken");
        }
        else {
            randomCodeGenerator();
            sendMail();
//            if()
            user1=user;
        }
    }
//    public void addUser(String code){
//        if(code.equals(g)){
//            userRepo.saveAndFlush(user1);
//        }
//    }

    public void addUser(User user){
        Optional<User> findByuserEmail=userRepo.findByuserEmail(user.getUserEmail());
        if(findByuserEmail.isPresent()){
            throw new IllegalStateException("Email Taken");
        }
        userRepo.saveAndFlush(user1);
    }



    public User getUserbyId(int userid) {
        Optional<User> userOptional=userRepo.findByuserid(userid);
        return userOptional.orElse(null);    }


//    @EventListener(ApplicationReadyEvent.class)
    public void sendMail(){
        emailService.sendEmail("riyadjannah2023@gmail.com", "Verify you Account", "Your Account Code is: "+g+"\n don't Share this Code with anyone");
    }
    public String randomCodeGenerator(){
        g=randomCode.GenerateCode();
        return g;
    }

    public String getCode() {
        return g;
    }
}


