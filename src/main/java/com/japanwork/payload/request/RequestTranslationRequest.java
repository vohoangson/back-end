package com.japanwork.payload.request;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestTranslationRequest {
	@JsonProperty("object_table_id")
	private Set<UUID> objectTableId;
	
	@JsonProperty("request_type")
	private String requestType;
	
	private String desc;
	
	@JsonProperty("language_id")
	private Set<UUID> languageId;
	
	public Set<UUID> getObjectTableId() {
		return objectTableId;
	}

	public void setObjectTableId(Set<UUID> objectTableId) {
		this.objectTableId = objectTableId;
	}

	public String getRequestType() {
		return requestType;
	}
	
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Set<UUID> getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Set<UUID> languageId) {
		this.languageId = languageId;
	}
}
