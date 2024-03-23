package com.example.GradProJM.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail(String toEmail, String Subjet, String Body){
        SimpleMailMessage msg=new SimpleMailMessage();
        msg.setFrom("TechSpotter <mohammadkadoumi77@gmail.com>");
        msg.setTo(toEmail);
        msg.setText(Body);
        msg.setSubject(Subjet);
        mailSender.send(msg);
        System.out.println("Mail Sent Successfully...");
    }
}
