package com.example.GradProJM;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
//@ComponentScan(basePackages = {"Service", "Controller","Repos"})
//@ComponentScan(basePackages = {"com.example.GradProJM", "Controller"})
//@ComponentScan(basePackages = {"com.GradProJM.GradProJmApplication", "Controller"})
public class GradProJmApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradProJmApplication.class, args);



	}



}
