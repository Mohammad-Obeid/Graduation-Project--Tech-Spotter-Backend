package com.example.GradProJM.Model;

import com.example.GradProJM.Services.orderItemService;
import com.example.GradProJM.Services.userService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class scheduler {
    private final userService userservice;
    private final orderItemService ordItmservice;

    public scheduler(userService userservice,orderItemService ordItmservice) {
        this.userservice = userservice;
        this.ordItmservice=ordItmservice;
    }

    @Scheduled(fixedDelay = 60000)
    public void sceduler() throws InterruptedException {
        userService.removeUnVerifiedUsers();
        userService.removeUnVerifiedUsersCodes();
        ordItmservice.changeOrderStatus();
        ordItmservice.changeOrderStatus2();

    }


}
