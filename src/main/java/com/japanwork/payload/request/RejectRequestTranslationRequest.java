package com.japanwork.payload.request;

import javax.validation.constraints.NotBlank;

public class RejectRequestTranslationRequest {
	@NotBlank
	private String reason;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
