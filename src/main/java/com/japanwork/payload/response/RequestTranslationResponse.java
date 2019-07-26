package com.japanwork.payload.response;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.japanwork.model.Language;

public class RequestTranslationResponse {
	private UUID id;
    
    private OwnerResponse owner;

    private ObjectTableResponse objectTable;
    
    private TranslatorResponse translator;
    
    private RequestTranslationStatusResponse status;
    
    @JsonProperty("request_type")
    private String requestType;
    
    @JsonProperty("converstaion")
    private ConversationResponse converstaion;
    
	private Language language;
    
    @JsonProperty("created_at")
    private Timestamp createdAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public OwnerResponse getOwner() {
		return owner;
	}

	public void setOwner(OwnerResponse owner) {
		this.owner = owner;
	}

	public ObjectTableResponse getObjectTable() {
		return objectTable;
	}

	public void setObjectTable(ObjectTableResponse objectTable) {
		this.objectTable = objectTable;
	}

	public TranslatorResponse getTranslator() {
		return translator;
	}

	public void setTranslator(TranslatorResponse translator) {
		this.translator = translator;
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

	public ConversationResponse getConverstaion() {
		return converstaion;
	}

	public void setConverstaion(ConversationResponse converstaion) {
		this.converstaion = converstaion;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public RequestTranslationResponse(UUID id, OwnerResponse owner, ObjectTableResponse objectTable,
			TranslatorResponse translator, RequestTranslationStatusResponse status, String requestType,
			ConversationResponse converstaion, Language language, Timestamp createdAt) {
		this.id = id;
		this.owner = owner;
		this.objectTable = objectTable;
		this.translator = translator;
		this.status = status;
		this.requestType = requestType;
		this.converstaion = converstaion;
		this.language = language;
		this.createdAt = createdAt;
	}

	public RequestTranslationResponse() {

	}
}
