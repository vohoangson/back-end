package com.japanwork.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */
public class LoginRequest {
    @NotBlank(message = "\"email\":\"Email is required!\"")
    @Email(message = "\"email\":\"Email is not correct!\"")
    private String email;
    
    @NotBlank(message = "\"password\":\"Password is required!\"")
	private String password;

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
}
