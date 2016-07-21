package com.mwelborn.r16.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "userId", "name", "email", "locked" })
public class User {
	private String userId;
	private String name;
	private String email;
	private boolean locked;

	public User() {
		userId = "";
		name = "";
		email = "";
		locked = false;
	}
	
	@Override
	public String toString() {
		return String.format("User: [userId: %s, name: %s, email: %s, locked: %s]", userId, name, email, locked);
	}
	
	@XmlElement(name="oprId")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement(name="accountLocked")
	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	
}
