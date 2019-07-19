package com.japanwork.payload.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse {
	private String name;
	
	private String email;
	
	private String role;
	
	@JsonProperty("language_code")
	private String languageCode;
	
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

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public UUID getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(UUID propertyId) {
		this.propertyId = propertyId;
	}

	public UserResponse(String name, String email, String role, String languageCode, UUID propertyId) {
		this.name = name;
		this.email = email;
		this.role = role;
		this.languageCode = languageCode;
		this.propertyId = propertyId;
	}

	public UserResponse() {
	}	
}
