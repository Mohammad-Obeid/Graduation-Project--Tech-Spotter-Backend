package com.example.GradProJM;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class GradProJmApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradProJmApplication.class, args);


//
		BCryptPasswordEncoder bb = new BCryptPasswordEncoder();
		System.out.println(bb.encode("123456"));
		System.out.println("/////////////////////////");
		System.out.println("/////////////////////////");
		System.out.println("/////////////////////////");
		System.out.println("/////////////////////////");
		System.out.println("/////////////////////////");

	}
}
