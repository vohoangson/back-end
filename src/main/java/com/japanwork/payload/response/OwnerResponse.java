package com.japanwork.payload.response;

import java.util.UUID;

public class OwnerResponse {
	private UUID id;
	private String name;
	private String avatar;
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public OwnerResponse(UUID id, String name, String avatar) {
		this.id = id;
		this.name = name;
		this.avatar = avatar;
	}

	public OwnerResponse() {
		
	}
}
