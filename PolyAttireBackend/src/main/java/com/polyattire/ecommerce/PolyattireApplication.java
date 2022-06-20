package com.polyattire.ecommerce;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.polyattire.ecommerce.entity.EcomRole;
import com.polyattire.ecommerce.entity.EcomUser;
import com.polyattire.ecommerce.service.UserService;

@SpringBootApplication
public class PolyattireApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolyattireApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncode() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	CommandLineRunner run(UserService userService) {
		return args ->{
			userService.saveRole(new EcomRole(null,"ROLE_USER"));
			userService.saveRole(new EcomRole(null,"ROLE_MANAGER"));
			userService.saveRole(new EcomRole(null,"ROLE_ADMIN"));
			userService.saveRole(new EcomRole(null,"ROLE_SUPER_ADMIN"));
			
			userService.saveUser(new EcomUser(null,"jack","jack@gmail.com","1234", new ArrayList<EcomRole>()));
			userService.saveUser(new EcomUser(null,"dummy","dumy@gmail.com","1234", new ArrayList<EcomRole>()));
			
			userService.attachRoleToUser("jack@gmail.com", "ROLE_ADMIN");
			userService.attachRoleToUser("jack@gmail.com", "ROLE_USER");
			userService.attachRoleToUser("dumy@gmail.com", "ROLE_USER");
		};
	}

}
