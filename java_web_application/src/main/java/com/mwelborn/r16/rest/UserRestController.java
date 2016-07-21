package com.mwelborn.r16.rest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mwelborn.r16.model.User;
import com.mwelborn.r16.service.UserListService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.springframework.stereotype.Component;

@Component
@Path("/users/{userId}")
public class UserRestController {
	private final UserListService userService;
	
	public UserRestController() {
		userService = UserListService.getInstance();
	}
	
	//@RequestMapping(value = "/api/users/{userId}", method = RequestMethod.GET)
	@GET
	@Produces("application/json")
	public Response getUser(@PathParam("userId") String userId) {
		System.out.printf("GET Request: %s%n", userId);
		User user = userService.findByKey(userId);
		Response response;
		if (user != null) {
			response = Response.ok(user).build();
		} else {
			response = Response.noContent().status(Response.Status.NOT_FOUND).build();
		}
		System.out.printf("Response: %s%n", response.toString());
		return response;
	}
	
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateUser(String json, @PathParam("userId") String userId) {
		System.out.printf("PUT Request: %s%n", userId);
		System.out.printf("Inbound Body: %s%n", json);
		JsonObject jsonObj = new JsonParser().parse(json).getAsJsonObject();
		Response response;
		User user = userService.findByKey(userId);
		if (user != null) {
			user.setName(jsonObj.get("name").getAsString());
			user.setEmail(jsonObj.get("email").getAsString());
			user.setLocked(jsonObj.get("accountLocked").getAsBoolean());
			userService.update(user);
			response = Response.ok(user).build();
		} else {
			response = Response.noContent().status(Response.Status.NOT_FOUND).build();
		}
		System.out.printf("Response: %s%n", response.toString());
		return response;
	}
}
