package com.japanwork.payload.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse {
	private String name;
	
	private String email;
	
	private String role;
	
	@JsonProperty("language_id")
	private UUID languageId;
	
	@JsonProperty("property_id")
	private UUID propertyId;

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public UUID getLanguageId() {
		return languageId;
	}

	public void setLanguageId(UUID languageId) {
		this.languageId = languageId;
	}

	public UUID getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(UUID propertyId) {
		this.propertyId = propertyId;
	}

	public UserResponse(String name, String email, String role, UUID languageId, UUID propertyId) {
		super();
		this.name = name;
		this.email = email;
		this.role = role;
		this.languageId = languageId;
		this.propertyId = propertyId;
	}

	public UserResponse() {
	}	
}
