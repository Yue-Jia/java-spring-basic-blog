package com.yueejia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		String c = passwordEncoder.encode("Coop-3IsAGoodOne");
//		System.out.println(c);
		SpringApplication.run(BlogApplication.class, args);
	}

}
