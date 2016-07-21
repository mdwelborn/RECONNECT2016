package com.mwelborn.r16.controller;

import com.mwelborn.r16.configuration.PeopleSoft;
import com.mwelborn.r16.model.User;
import com.mwelborn.r16.service.UserListService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SiteController {
	
	private final UserListService userService;
	
	public SiteController() {
		userService = UserListService.getInstance();
	}
	
	@RequestMapping("/")
	public String index(Model model) {
		return "/index";
	}
	
	@RequestMapping(value="/users", method = RequestMethod.GET)
	public String users(Model model) {
		model.addAttribute("users", userService.findAll());
		model.addAttribute("userCreateUpdate", new User());
		return "/users";
	}
	
	@RequestMapping(value="/users/{userId}", method = RequestMethod.GET)
	public String users(Model model, @PathVariable String userId) {
		User user = userService.findByKey(userId);
		model.addAttribute("userCreateUpdate", user);
		model.addAttribute("users", userService.findAll());
		return "/users";
	}
	
	@RequestMapping(value = "/users/createUpdate", method = RequestMethod.POST)
	public String userCreateUpdate(@ModelAttribute("userCreateUpdate") User user, 
			BindingResult result, Model model) {
		if (userService.findByKey(user.getUserId()) != null) {
			userService.update(user);
		} else {
			userService.save(user);
			
			Client client = ClientBuilder.newClient();
			String path = user.getUserId();
			Response response = client.target(PeopleSoft.BASE_URL + "/PSIGW/RESTListeningConnector/PSFT_EP/R16_USER.v1/")
					.path(path).request(MediaType.APPLICATION_JSON).get();
			
			if (response.getStatus() != Response.Status.OK.getStatusCode()) {
				System.out.println("User does not exist in PeopleSoft. Sending a post request");
				Response postResponse = client.target(PeopleSoft.BASE_URL + "/PSIGW/RESTListeningConnector/PSFT_EP/R16_USER.v1/create")
						.request(MediaType.APPLICATION_JSON).post(Entity.entity(user, MediaType.APPLICATION_JSON));
				
				System.out.println("POST Request: " + Entity.entity(user, MediaType.APPLICATION_JSON).toString());
				
				if (postResponse.getStatus() == Response.Status.CREATED.getStatusCode()) {
					System.out.printf("User %s created on PeopleSoft%n", user.getUserId());
				} else {
					System.out.printf("Failed to create user: %s%n", postResponse.toString());
				}
				
			} else {
				System.out.println("User already exists");
			}
			
		}
		return "redirect:/users";
	}
}
