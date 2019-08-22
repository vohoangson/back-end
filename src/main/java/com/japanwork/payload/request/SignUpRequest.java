package com.japanwork.payload.request;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignUpRequest {
    @NotBlank
    @Size(min=8, max=128)
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min=8, max=32)
    private String password;

    @NotNull
    @JsonProperty("country_id")
    private UUID countryId;
    
    @NotBlank
    @Pattern(regexp = "COMPANY|CANDIDATE|TRANSLATOR")
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
