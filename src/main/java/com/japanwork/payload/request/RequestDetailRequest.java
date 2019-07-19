package com.japanwork.payload.request;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestDetailRequest {
	@JsonProperty("object_table_id")
	private UUID objectTableId;
	
	@JsonProperty("request_type")
	private String requestType;
	
	private String desc;
	
	@JsonProperty("language_id")
	private UUID languageId;
	
	public UUID getObjectTableId() {
		return objectTableId;
	}
	
	public void setObjectTableId(UUID objectTableId) {
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

	public UUID getLanguageId() {
		return languageId;
	}

	public void setLanguageId(UUID languageId) {
		this.languageId = languageId;
	}
}
