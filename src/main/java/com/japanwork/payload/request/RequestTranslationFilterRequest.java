package com.japanwork.payload.request;

import java.util.Set;
import java.util.UUID;

public class RequestTranslationFilterRequest {
	private String name;
	
	private Set<UUID> languageIds;

	private Set<String> requestTypes;
	
	private String postDate;
	
	private boolean yourRequest;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UUID> getLanguageIds() {
		return languageIds;
	}

	public void setLanguageIds(Set<UUID> languageIds) {
		this.languageIds = languageIds;
	}

	public Set<String> getRequestTypes() {
		return requestTypes;
	}

	public void setRequestTypes(Set<String> requestTypes) {
		this.requestTypes = requestTypes;
	}

	public String getPostDate() {
		return postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public boolean isYourRequest() {
		return yourRequest;
	}

	public void setYourRequest(boolean yourRequest) {
		this.yourRequest = yourRequest;
	}
}
