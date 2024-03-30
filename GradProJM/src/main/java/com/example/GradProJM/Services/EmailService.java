package com.example.GradProJM.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendVerificationCodeEmail(String toEmail, String verificationCode,String userName) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("TechSpotter <mohammadkadoumi77@gmail.com>");
        msg.setTo(toEmail);
        msg.setSubject("Verify your email address");
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);
        String body = "Dear "+userName+",\n\n"
                + "Thank you for signing up for TechSpotter! To complete your registration, please use the following verification code:\n\n"
                + "Verification Code: " + verificationCode + "\n\n"
                + "This code will expire at: " + expirationTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n\n"
                + "Please verify your email within 10 minutes. If you didn't request this verification, you can safely ignore this email.\n\n"
                + "Best regards,\n"
                + "TechSpotter Team";
        msg.setText(body);
        mailSender.send(msg);
        System.out.println("Verification Email Sent Successfully to " + toEmail);
    }





}
