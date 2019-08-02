package com.japanwork.payload.request;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageRequest {
	private String content;
	private String type;
	@JsonProperty("objectable_id")
	private UUID objectableId;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UUID getObjectableId() {
		return objectableId;
	}

	public void setObjectableId(UUID objectableId) {
		this.objectableId = objectableId;
	}
}
