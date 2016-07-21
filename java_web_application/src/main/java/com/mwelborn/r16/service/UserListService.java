package com.mwelborn.r16.service;

import com.mwelborn.r16.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserListService {
	private static final UserListService INSTANCE = new UserListService();
	private List<User> users;
	
	private UserListService() {
		users = new ArrayList<>();
	}
	
	public static UserListService getInstance() {
		return INSTANCE;
	}
	
	public List<User> findAll() {
		return new ArrayList<>(users);
	}
	
	public User findByKey(String userId) {
		for (User user : users) {
			if (user.getUserId().equals(userId)) {
				return user;
			}
		}
		return null;
	}
	
	public void save(User user) {
		users.add(user);
	}
	
	public void update(User user) {
		for (int i = 0; i < users.size(); i++) {
			User u = users.get(i);
			if (u.getUserId().equals(user.getUserId())) {
				users.remove(i);
				users.add(i, user);
			}
		}
	}
	
	public void printUsers() {
		for (User user : users) {
			System.out.println(user);
		}
	}
}
