package com.japanwork.payload.response;

import java.util.List;

public class BaseErrorResponse {
	private String code;
	private List<String> message;
	public BaseErrorResponse(String code, List<String> message) {
		super();
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<String> getMessage() {
		return message;
	}
	public void setMessage(List<String> message) {
		this.message = message;
	}
}
