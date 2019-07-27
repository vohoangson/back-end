package com.japanwork.payload.response;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestTranslationStatusResponse {
	private UUID id;
	
	@JsonProperty("creator_id")
	private UUID creatorId;
	
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

	public UUID getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(UUID creatorId) {
		this.creatorId = creatorId;
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
	
	public RequestTranslationStatusResponse(UUID id, UUID creatorId, String reason, String status,
			Timestamp createdAt) {
		this.id = id;
		this.creatorId = creatorId;
		this.reason = reason;
		this.status = status;
		this.createdAt = createdAt;
	}

	public RequestTranslationStatusResponse() {

	}	
}
