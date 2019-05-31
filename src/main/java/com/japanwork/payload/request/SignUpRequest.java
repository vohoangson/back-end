package com.japanwork.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */

public class SignUpRequest {
    @NotBlank(message = "\"name\":\"Name is required!\"")
    private String name;

    @NotBlank(message = "\"email\":\"Email is required!\"")
    @Email(message = "\"email\":\"Email is not correct!\"")
    private String email;

    @NotBlank(message = "\"password\":\"Password is required!\"")
    private String password;

    @NotBlank(message = "\"role\":\"You are a business or an individual?\"")
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
    
}
