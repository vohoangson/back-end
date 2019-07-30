package com.japanwork.payload.request;

import java.util.List;

public class RequestTranslationFilterRequest {
	private String name;
	
	private List<String> languageIds;

	private List<String> requestTypes;
	
	private String postDate;
	
	private boolean yourRequest;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getLanguageIds() {
		return languageIds;
	}

	public void setLanguageIds(List<String> languageIds) {
		this.languageIds = languageIds;
	}

	public List<String> getRequestTypes() {
		return requestTypes;
	}

	public void setRequestTypes(List<String> requestTypes) {
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
