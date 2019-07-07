package com.japanwork.payload.response;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobApplicationResponse {
	private UUID id;
    
    private JobResponse job;
    
    private CandidateResponse candidate;
    
    private TranslatorResponse translator;
    
    @JsonProperty("submit_application_at")
    private Timestamp submitApplicationAt;
    
    @JsonProperty("approve_application_at")
    private Timestamp approveApplicationAt;
    
    @JsonProperty("reject_application_at")
    private Timestamp rejectApplicationAt;
    
    @JsonProperty("candidate_support_conversaion")
    private ConversationResponse candidateSupportConversaion;
    
    @JsonProperty("company_support_conversation")
    private ConversationResponse companySupportConversation;
    
    @JsonProperty("all_conversation_id")
    private ConversationResponse allConversation;
    
    @JsonProperty("application_succeed_at")
    private Timestamp applicationSucceedAt;
    
    @JsonProperty("cancel_reason")
    private String cancelReason;
    
    @JsonProperty("user_cancel")
    private int userCancel;
    
    @JsonProperty("cancel_at")
    private Timestamp cancelAt;
    
    @JsonProperty("status")
    private int status;

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

	public Timestamp getSubmitApplicationAt() {
		return submitApplicationAt;
	}

	public void setSubmitApplicationAt(Timestamp submitApplicationAt) {
		this.submitApplicationAt = submitApplicationAt;
	}

	public Timestamp getApproveApplicationAt() {
		return approveApplicationAt;
	}

	public void setApproveApplicationAt(Timestamp approveApplicationAt) {
		this.approveApplicationAt = approveApplicationAt;
	}

	public Timestamp getRejectApplicationAt() {
		return rejectApplicationAt;
	}

	public void setRejectApplicationAt(Timestamp rejectApplicationAt) {
		this.rejectApplicationAt = rejectApplicationAt;
	}

	public ConversationResponse getCandidateSupportConversaion() {
		return candidateSupportConversaion;
	}

	public void setCandidateSupportConversaion(ConversationResponse candidateSupportConversaion) {
		this.candidateSupportConversaion = candidateSupportConversaion;
	}

	public ConversationResponse getCompanySupportConversation() {
		return companySupportConversation;
	}

	public void setCompanySupportConversation(ConversationResponse companySupportConversation) {
		this.companySupportConversation = companySupportConversation;
	}

	public ConversationResponse getAllConversation() {
		return allConversation;
	}

	public void setAllConversation(ConversationResponse allConversation) {
		this.allConversation = allConversation;
	}

	public Timestamp getApplicationSucceedAt() {
		return applicationSucceedAt;
	}

	public void setApplicationSucceedAt(Timestamp applicationSucceedAt) {
		this.applicationSucceedAt = applicationSucceedAt;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public int getUserCancel() {
		return userCancel;
	}

	public void setUserCancel(int userCancel) {
		this.userCancel = userCancel;
	}

	public Timestamp getCancelAt() {
		return cancelAt;
	}

	public void setCancelAt(Timestamp cancelAt) {
		this.cancelAt = cancelAt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public JobApplicationResponse(UUID id, JobResponse job, CandidateResponse candidate, TranslatorResponse translator,
			Timestamp submitApplicationAt, Timestamp approveApplicationAt, Timestamp rejectApplicationAt,
			ConversationResponse candidateSupportConversaion, ConversationResponse companySupportConversation,
			ConversationResponse allConversation, Timestamp applicationSucceedAt, String cancelReason, int userCancel,
			Timestamp cancelAt, int status) {
		super();
		this.id = id;
		this.job = job;
		this.candidate = candidate;
		this.translator = translator;
		this.submitApplicationAt = submitApplicationAt;
		this.approveApplicationAt = approveApplicationAt;
		this.rejectApplicationAt = rejectApplicationAt;
		this.candidateSupportConversaion = candidateSupportConversaion;
		this.companySupportConversation = companySupportConversation;
		this.allConversation = allConversation;
		this.applicationSucceedAt = applicationSucceedAt;
		this.cancelReason = cancelReason;
		this.userCancel = userCancel;
		this.cancelAt = cancelAt;
		this.status = status;
	}

	public JobApplicationResponse() {
		super();
	}
}
