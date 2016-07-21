package com.mwelborn.r16;

import com.mwelborn.r16.model.User;
import com.mwelborn.r16.service.UserListService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class R16UserSiteApp {
	private static UserListService userService;
	
    public static void main(String[] args) {
		userService = UserListService.getInstance();
		
		User user = new User();
		user.setUserId("ABROWN");
		user.setName("Aaron Brown");
		user.setEmail("");
		user.setLocked(true);
		userService.save(user);
		
		user = new User();
		user.setUserId("ACALDER");
		user.setName("Alexis Calder");
		user.setEmail("");
		user.setLocked(true);
		userService.save(user);
		
		user = new User();
		user.setUserId("BBELL");
		user.setName("Brad Bell");
		user.setEmail("");
		user.setLocked(true);
		userService.save(user);
		
		SpringApplication.run(R16UserSiteApp.class, args);
    }
}
