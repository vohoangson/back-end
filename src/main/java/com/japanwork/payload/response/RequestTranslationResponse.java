package com.japanwork.payload.response;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestTranslationResponse {
	private UUID id;
    
	@JsonProperty("owner_id")
    private UUID ownerId;
	
	@JsonProperty("objecttable_id")
    private UUID objectTableId;
    
	@JsonProperty("translator_id")
    private UUID translatorId;
    
    private RequestTranslationStatusResponse status;
    
    @JsonProperty("request_type")
    private String requestType;
    
    @JsonProperty("converstaion_id")
    private UUID converstaionId;
    
    @JsonProperty("language_code")
	private String languageCode;
    
    @JsonProperty("created_at")
    private Timestamp createdAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}

	public UUID getObjectTableId() {
		return objectTableId;
	}

	public void setObjectTableId(UUID objectTableId) {
		this.objectTableId = objectTableId;
	}

	public UUID getTranslatorId() {
		return translatorId;
	}

	public void setTranslatorId(UUID translatorId) {
		this.translatorId = translatorId;
	}

	public RequestTranslationStatusResponse getStatus() {
		return status;
	}

	public void setStatus(RequestTranslationStatusResponse status) {
		this.status = status;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public UUID getConverstaionId() {
		return converstaionId;
	}

	public void setConverstaionId(UUID converstaionId) {
		this.converstaionId = converstaionId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public RequestTranslationResponse() {

	}

	public RequestTranslationResponse(UUID id, UUID ownerId, UUID objectTableId, UUID translatorId,
			RequestTranslationStatusResponse status, String requestType, UUID converstaionId, String languageCode,
			Timestamp createdAt) {
		this.id = id;
		this.ownerId = ownerId;
		this.objectTableId = objectTableId;
		this.translatorId = translatorId;
		this.status = status;
		this.requestType = requestType;
		this.converstaionId = converstaionId;
		this.languageCode = languageCode;
		this.createdAt = createdAt;
	}
    
}
