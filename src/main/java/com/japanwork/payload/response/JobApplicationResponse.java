package com.japanwork.payload.response;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobApplicationResponse {
	private UUID id;
    
    private JobResponse job;
    
    private CandidateResponse candidate;
    
    private TranslatorResponse translator;
    
    @JsonProperty("candidate_support_conversaion_id")
    private UUID candidateSupportConversaionId;
    
    @JsonProperty("company_support_conversation_id")
    private UUID companySupportConversationId;
    
    @JsonProperty("all_conversation_id")
    private UUID allConversation;
    
    private RequestTranslationStatusResponse status;

    @JsonProperty("created_at")
    private Timestamp createdAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public JobResponse getJob() {
		return job;
	}

	public void setJob(JobResponse job) {
		this.job = job;
	}

	public CandidateResponse getCandidate() {
		return candidate;
	}

	public void setCandidate(CandidateResponse candidate) {
		this.candidate = candidate;
	}

	public TranslatorResponse getTranslator() {
		return translator;
	}

	public void setTranslator(TranslatorResponse translator) {
		this.translator = translator;
	}

	public UUID getCandidateSupportConversaionId() {
		return candidateSupportConversaionId;
	}

	public void setCandidateSupportConversaionId(UUID candidateSupportConversaionId) {
		this.candidateSupportConversaionId = candidateSupportConversaionId;
	}

	public UUID getCompanySupportConversationId() {
		return companySupportConversationId;
	}

	public void setCompanySupportConversationId(UUID companySupportConversationId) {
		this.companySupportConversationId = companySupportConversationId;
	}

	public UUID getAllConversation() {
		return allConversation;
	}

	public void setAllConversation(UUID allConversation) {
		this.allConversation = allConversation;
	}

	public RequestTranslationStatusResponse getStatus() {
		return status;
	}

	public void setStatus(RequestTranslationStatusResponse status) {
		this.status = status;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public JobApplicationResponse() {

	}

	public JobApplicationResponse(UUID id, JobResponse job, CandidateResponse candidate, TranslatorResponse translator,
			UUID candidateSupportConversaionId, UUID companySupportConversationId, UUID allConversation,
			RequestTranslationStatusResponse status, Timestamp createdAt) {
		this.id = id;
		this.job = job;
		this.candidate = candidate;
		this.translator = translator;
		this.candidateSupportConversaionId = candidateSupportConversaionId;
		this.companySupportConversationId = companySupportConversationId;
		this.allConversation = allConversation;
		this.status = status;
		this.createdAt = createdAt;
	}
}
