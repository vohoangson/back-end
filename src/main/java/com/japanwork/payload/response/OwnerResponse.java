package com.japanwork.payload.response;

import java.util.UUID;

public class OwnerResponse {
	private UUID id;
	private String name;
	private String role;
	private String avatar;
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public OwnerResponse(UUID id, String name, String role, String avatar) {
		this.id = id;
		this.name = name;
		this.role = role;
		this.avatar = avatar;
	}

	public OwnerResponse() {
	}
}
