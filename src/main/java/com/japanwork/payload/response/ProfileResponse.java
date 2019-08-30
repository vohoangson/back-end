package com.japanwork.payload.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileResponse {
	@JsonProperty("user_id")
	private UUID userId;
	
	@JsonProperty("property_id")
	private UUID propertyId;
	
	private String name;
	
	private String role;
	
	private String avatar;
	
	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UUID getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(UUID propertyId) {
		this.propertyId = propertyId;
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

	public ProfileResponse(UUID userId, UUID propertyId, String name, String role, String avatar) {
		this.userId = userId;
		this.propertyId = propertyId;
		this.name = name;
		this.role = role;
		this.avatar = avatar;
	}

	public ProfileResponse() {
	}
}
