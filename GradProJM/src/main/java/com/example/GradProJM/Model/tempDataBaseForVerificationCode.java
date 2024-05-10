package com.example.GradProJM.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "temp")
public class tempDataBaseForVerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userEmail;

    private String verificationCode;

    private String time;

    public tempDataBaseForVerificationCode() {
    }

    public tempDataBaseForVerificationCode(int id, String userEmail, String verificationCode, String time) {
        this.id = id;
        this.userEmail = userEmail;
        this.verificationCode = verificationCode;
        this.time=String.valueOf(LocalDateTime.now());
    }

    public tempDataBaseForVerificationCode(String userEmail, String verificationCode, String time) {
        this.userEmail = userEmail;
        this.verificationCode = verificationCode;
        this.time=String.valueOf(LocalDateTime.now());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
