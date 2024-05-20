package com.example.GradProJM.Services;

import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.ProductReport;
import com.example.GradProJM.Model.Shop_Products;
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
import java.util.Optional;

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


    public void sendDeletionEmailForShop(String shopName, String userEmail, String prodBarcode) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("TechSpotter <mohammadkadoumi77@gmail.com>");
        msg.setTo(userEmail);
        msg.setSubject("Product Removal Notification");

        String body = "Dear " + shopName + ",\n\n"
                + "We regret to inform you that your product with the barcode " + prodBarcode + " has been deleted from our platform. "
                + "This action was taken following multiple reports concerning the product.\n\n"
                + "We apologize for any inconvenience this may cause. If you have any questions or need further assistance, please feel free to contact our support team.\n\n"
                + "Best regards,\n"
                + "TechSpotter Team";
        msg.setText(body);
        mailSender.send(msg);

    }

    public void sendOrderDeletionEmailForShop(Shop_Products product, Customer customer) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("TechSpotter <mohammadkadoumi77@gmail.com>");
//        todo: msg.setTo(product.getShop().getUser().getUserEmail());
        msg.setTo("mohammadkadoumi77@yahoo.com");
        msg.setSubject("Order Canceled Notification");

        String body = "Dear " + product.getShop().getShopName() + ",\n\n"
                + "We are sorry to inform you that our customer, " + customer.getUser().getUserName() + ", has cancelled their order from your shop. "
                + "The cancelled order contains the following product:\n\n"
                + product.getProduct() + "\n\n"
                + "Best regards,\n"
                + "TechSpotter Team";
        msg.setText(body);
        mailSender.send(msg);
    }

    public void sendReportAcceptionEmail(Optional<Customer> cust, Optional<ProductReport> rep) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("TechSpotter <mohammadkadoumi77@gmail.com>");
//        todo: msg.setTo(cust.get().getUser().getUserEmail());
        msg.setTo("mohammadkadoumi77@yahoo.com");
        msg.setSubject("Your Report is Being Processed");

        String body = "Dear " + cust.get().getUser().getUserName() + ",\n\n"
                + "Thank you for submitting a report regarding the following product: "
                +"{ Product Barcode: "+rep.get().getProduct().getProduct().getProductBarcode()+
                ", Product Name: "+rep.get().getProduct().getProduct().getProductName()+
                ", Product Category: "+rep.get().getProduct().getProduct().getProductCategory()+"}\n\n"
                + "We want to assure you that your report is currently under processing. We appreciate your patience and will keep you updated on any progress.\n\n"
                + "If you have any questions or need further assistance, please do not hesitate to contact our support team.\n\n"
                + "Best regards,\n"
                + "TechSpotter Team";
        msg.setText(body);
        mailSender.send(msg);
    }
}
