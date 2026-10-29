package com.hotel.sicig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SicigApplication {

	public static void main(String[] args) {
		SpringApplication.run(SicigApplication.class, args);


		System.out.println(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("1524windows"));

	}
	
}
