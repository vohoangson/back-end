package com.japanwork.payload.request;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageRequest {
	@NotBlank
	@Size(max = 2000)
	private String content;
	
	@NotBlank
	@Size(max = 128)
	private String type;
	
	@NotNull
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
