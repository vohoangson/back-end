package com.japanwork.payload.response;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestTranslationResponse {
	private UUID id;
	
    private String name;
    
    private OwnerResponse owner;

	@JsonProperty("objectable_id")
    private UUID objectableId;
    
	@JsonProperty("translator")
    private TranslatorResponse translatorResponse;
    
    private RequestTranslationStatusResponse status;
    
    @JsonProperty("request_type")
    private String requestType;
    
    @JsonProperty("conversation_id")
    private UUID conversationId;
    
    @JsonProperty("language_id")
	private UUID languageId;
    
    @JsonProperty("created_at")
    private Timestamp createdAt;

    private String desc;
    
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

	public OwnerResponse getOwner() {
		return owner;
	}

	public void setOwner(OwnerResponse owner) {
		this.owner = owner;
	}

	public UUID getObjectableId() {
		return objectableId;
	}

	public void setObjectableId(UUID objectableId) {
		this.objectableId = objectableId;
	}

	public TranslatorResponse getTranslatorResponse() {
		return translatorResponse;
	}

	public void setTranslatorResponse(TranslatorResponse translatorResponse) {
		this.translatorResponse = translatorResponse;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public RequestTranslationResponse(UUID id, String name, OwnerResponse owner, UUID objectableId,
			TranslatorResponse translatorResponse, RequestTranslationStatusResponse status, String requestType,
			UUID conversationId, UUID languageId, Timestamp createdAt, String desc) {
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.objectableId = objectableId;
		this.translatorResponse = translatorResponse;
		this.status = status;
		this.requestType = requestType;
		this.conversationId = conversationId;
		this.languageId = languageId;
		this.createdAt = createdAt;
		this.desc = desc;
	}

	public RequestTranslationResponse() {

	}
}
