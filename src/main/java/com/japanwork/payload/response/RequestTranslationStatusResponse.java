package com.japanwork.payload.response;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestTranslationStatusResponse {
	private UUID id;
	
	@JsonProperty("user_create_id")
	private UUID userCreateId;
	
	private String reason;
	
	private String status;
	
	@JsonProperty("created_at")
	private Timestamp createdAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getUserCreateId() {
		return userCreateId;
	}

	public void setUserCreateId(UUID userCreateId) {
		this.userCreateId = userCreateId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public RequestTranslationStatusResponse(UUID id, UUID userCreateId, String reason, String status,
			Timestamp createdAt) {
		this.id = id;
		this.userCreateId = userCreateId;
		this.reason = reason;
		this.status = status;
		this.createdAt = createdAt;
	}

	public RequestTranslationStatusResponse() {

	}	
}
