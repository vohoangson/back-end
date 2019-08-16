package com.japanwork.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePasswordRequest {
	@JsonProperty("old_password")
	@NotBlank
    @Size(min=8, max=32)
	private String oldPassword;
	
	@JsonProperty("new_password")
	@NotBlank
    @Size(min=8, max=32)
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
