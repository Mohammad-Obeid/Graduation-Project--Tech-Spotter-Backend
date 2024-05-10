package com.example.GradProJM.Model;

import com.example.GradProJM.Services.userService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class scheduler {
    private final userService userservice;

    public scheduler(userService userservice) {
        this.userservice = userservice;
    }

    @Scheduled(fixedDelay = 60000)
    public void sceduler() throws InterruptedException {
        LocalDateTime current =LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String fromattedDatetime = current.format(format);

        userService.removeUnVerifiedUsers();
        userService.removeUnVerifiedUsersCodes();

    }


}
