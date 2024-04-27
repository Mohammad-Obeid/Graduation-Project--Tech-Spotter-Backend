package com.example.GradProJM.Model;

public class LoginRequest {
    private String UserNameOrEmail;
    private String Password;

    public LoginRequest(String userNameOrEmail, String password) {
        UserNameOrEmail = userNameOrEmail;
        Password = password;
    }

    public String getUserNameOrEmail() {
        return UserNameOrEmail;
    }

    public void setUserNameOrEmail(String userNameOrEmail) {
        UserNameOrEmail = userNameOrEmail;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
