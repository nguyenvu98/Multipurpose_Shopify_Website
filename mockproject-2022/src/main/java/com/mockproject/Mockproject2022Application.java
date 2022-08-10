package com.mockproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class Mockproject2022Application {

	public static void main(String[] args) {
		SpringApplication.run(Mockproject2022Application.class, args);
		
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		String hashPass = "$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO";
		String rawPass = "111";
		System.out.println(bcrypt.matches(rawPass, hashPass));
	}

}
