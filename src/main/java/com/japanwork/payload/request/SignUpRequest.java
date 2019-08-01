package com.japanwork.payload.request;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignUpRequest {
    @NotBlank(message = "username_required")
    private String name;

    @NotBlank(message = "email_required")
    @Email(message = "invalid_email_format")
    private String email;

    @NotBlank(message = "password_required")
    @Size(min=8,message = "invalid_password_length")
    private String password;

//    @NotNull(message = "country_required")
    @JsonProperty("country_id")
    private UUID countryId;
    
    @NotBlank(message = "role_required")
    @Pattern(regexp = "COMPANY|CANDIDATE|TRANSLATOR",message = "invalid_role_format")
    private String role;
    
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public UUID getCountryId() {
		return countryId;
	}

	public void setCountryId(UUID countryId) {
		this.countryId = countryId;
	}
}
