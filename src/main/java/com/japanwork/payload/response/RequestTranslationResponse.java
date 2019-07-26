package com.japanwork.payload.response;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.japanwork.model.Language;

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
    
    @JsonProperty("language_id")
	private UUID languageId;
    
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

	public UUID getLanguageId() {
		return languageId;
	}

	public void setLanguageId(UUID languageId) {
		this.languageId = languageId;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public RequestTranslationResponse(UUID id, UUID ownerId, UUID objectTableId, UUID translatorId,
			RequestTranslationStatusResponse status, String requestType, UUID converstaionId, UUID languageId,
			Timestamp createdAt) {
		this.id = id;
		this.ownerId = ownerId;
		this.objectTableId = objectTableId;
		this.translatorId = translatorId;
		this.status = status;
		this.requestType = requestType;
		this.converstaionId = converstaionId;
		this.languageId = languageId;
		this.createdAt = createdAt;
	}

	public RequestTranslationResponse() {

	}
}
