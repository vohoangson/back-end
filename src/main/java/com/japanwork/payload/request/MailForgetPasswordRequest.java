package com.japanwork.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MailForgetPasswordRequest {
	@NotBlank(message = "email_required")
    @Email(message = "invalid_email_format")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
