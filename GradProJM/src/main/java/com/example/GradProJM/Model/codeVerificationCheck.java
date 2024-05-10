package com.example.GradProJM.Model;

public class codeVerificationCheck {
    private String userEmail,verificationCode;

    public codeVerificationCheck() {
    }

    public codeVerificationCheck(String userEmail, String verificationCode) {
        this.userEmail = userEmail;
        this.verificationCode = verificationCode;
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


}
