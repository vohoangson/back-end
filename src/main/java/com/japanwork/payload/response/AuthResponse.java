package com.japanwork.payload.response;

public class AuthResponse {
    private String access_token;
    private String token_type = "Bearer";
    
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public AuthResponse(String access_token) {
		super();
		this.access_token = access_token;
	}
}
