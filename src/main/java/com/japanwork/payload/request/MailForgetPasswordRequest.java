package com.japanwork.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MailForgetPasswordRequest {
	@NotBlank(message = "\"email\":\"Email is required!\"")
    @Email(message = "\"email\":\"Email is not correct!\"")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
