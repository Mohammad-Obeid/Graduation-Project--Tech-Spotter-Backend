package com.example.GradProJM.Model;

import com.example.GradProJM.Services.userService;
import org.apache.juli.logging.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class sceduler {
    private final userService userservice;

    public sceduler(userService userservice) {
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
