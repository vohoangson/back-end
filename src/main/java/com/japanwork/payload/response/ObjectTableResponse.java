package com.japanwork.payload.response;

import java.util.UUID;

public class ObjectTableResponse {
	private UUID id;
	private String name;
	
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

	public ObjectTableResponse(UUID id, String name) {
		this.id = id;
		this.name = name;
	}

	public ObjectTableResponse() {

	}	
}
