package com.japanwork.payload;

public class ConfirmRegistrationTokenResponse {
	private String message;
	private String code;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public ConfirmRegistrationTokenResponse(String message, String code) {
		super();
		this.message = message;
		this.code = code;
	}
	
}
