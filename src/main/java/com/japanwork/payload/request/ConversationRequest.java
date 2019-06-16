package com.japanwork.payload.request;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConversationRequest {	
	@JsonProperty("company_id")
	private UUID companyId;
	
	@JsonProperty("candidate_id")
	private UUID candidateId;
	
	public UUID getCompanyId() {
		return companyId;
	}

	public void setCompanyId(UUID companyId) {
		this.companyId = companyId;
	}

	public UUID getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(UUID candidateId) {
		this.candidateId = candidateId;
	}
}
