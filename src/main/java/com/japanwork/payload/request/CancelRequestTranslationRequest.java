package com.japanwork.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CancelRequestTranslationRequest {
	@NotBlank
	@Size(max=1000)
	private String reason;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
