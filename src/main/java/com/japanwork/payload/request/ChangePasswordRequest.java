package com.japanwork.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePasswordRequest {
	@JsonProperty("old_password")
	@NotBlank(message = "\"old_password\":\"Old password is required!\"")
    @Size(min=8,message = "\"old_password\":\"Old password at least 8 characters!\"")
	private String oldPassword;
	
	@JsonProperty("new_password")
	@NotBlank(message = "\"new_password\":\"New password is required!\"")
    @Size(min=8,message = "\"new_password\":\"New password at least 8 characters!\"")
	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
