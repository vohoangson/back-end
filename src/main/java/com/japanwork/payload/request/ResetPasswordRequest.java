package com.japanwork.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ResetPasswordRequest {
	@NotBlank(message = "\"email\":\"Email is required!\"")
    @Email(message = "\"email\":\"Email is not correct!\"")
    private String email;

    @NotBlank(message = "\"password\":\"Password is required!\"")
    @Size(min=8,message = "\"password\":\"Password at least 8 characters!\"")
    private String password;

    @NotBlank(message = "\"Code\":\"Confirmation code is required!\"")
    @Size(min=6, max = 6,message = "\"Code\":\"Confirmation code has 6 characters!\"")
    private String code;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
