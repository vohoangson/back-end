package com.japanwork.payload.request;

import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestTranslationRequest {
	@NotNull
	@JsonProperty("objectable_id")
	private Set<UUID> objectableId;
	
	@NotBlank
	@Size(max = 128)
	@JsonProperty("request_type")
	private String requestType;
	
	@NotBlank
	@Size(max = 2000)
	private String desc;
	
	@NotNull
	@JsonProperty("language_id")
	private Set<UUID> languageId;

	public Set<UUID> getObjectableId() {
		return objectableId;
	}

	public void setObjectableId(Set<UUID> objectableId) {
		this.objectableId = objectableId;
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
