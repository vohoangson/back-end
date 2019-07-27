package com.japanwork.payload.response;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestTranslationResponse {
	private UUID id;
    
	@JsonProperty("owner_id")
    private UUID ownerId;

	@JsonProperty("objectable_id")
    private UUID objectableId;
    
	@JsonProperty("translator_id")
    private UUID translatorId;
    
    private RequestTranslationStatusResponse status;
    
    @JsonProperty("request_type")
    private String requestType;
    
    @JsonProperty("conversation_id")
    private UUID conversationId;
    
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

	public UUID getObjectableId() {
		return objectableId;
	}

	public void setObjectableId(UUID objectableId) {
		this.objectableId = objectableId;
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

	public UUID getConversationId() {
		return conversationId;
	}

	public void setConversationId(UUID conversationId) {
		this.conversationId = conversationId;
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

	public RequestTranslationResponse(UUID id, UUID ownerId, UUID objectableId, UUID translatorId,
			RequestTranslationStatusResponse status, String requestType, UUID conversationId, UUID languageId,
			Timestamp createdAt) {
		super();
		this.id = id;
		this.ownerId = ownerId;
		this.objectableId = objectableId;
		this.translatorId = translatorId;
		this.status = status;
		this.requestType = requestType;
		this.conversationId = conversationId;
		this.languageId = languageId;
		this.createdAt = createdAt;
	}

	public RequestTranslationResponse() {

	}
}
