package com.japanwork.payload.response;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestDetailResponse {
	private UUID id;
    
	@JsonProperty("owner_id")
    private UUID ownerId;
	
	@JsonProperty("object_table_id")
    private UUID objectTableId;
    
	@JsonProperty("helper_id")
    private UUID helperId;
    
    private String status;
    
    @JsonProperty("request_type")
    private String requestType;
    
    @JsonProperty("converstaion_id")
    private UUID converstaionId;
    
    @JsonProperty("language_code")
	private String languageCode;
    
    @JsonProperty("approve_at")
    private Timestamp approveAt;
    
    @JsonProperty("complete_at")
    private Timestamp completeAt;
    
    @JsonProperty("cancel_at")
    private Timestamp cancelAt;
    
    @JsonProperty("reason_cancel")
    private String reasonCancel;
    
    @JsonProperty("user_cancel")
    private UUID userCancel;
    
    @JsonProperty("reject_at")
    private Timestamp rejectAt;
    
    @JsonProperty("reason_reject")
    private String reasonReject;
    
    @JsonProperty("created_at")
    private Timestamp createdAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}

	public UUID getObjectTableId() {
		return objectTableId;
	}

	public void setObjectTableId(UUID objectTableId) {
		this.objectTableId = objectTableId;
	}

	public UUID getHelperId() {
		return helperId;
	}

	public void setHelperId(UUID helperId) {
		this.helperId = helperId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public UUID getConverstaionId() {
		return converstaionId;
	}

	public void setConverstaionId(UUID converstaionId) {
		this.converstaionId = converstaionId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public Timestamp getApproveAt() {
		return approveAt;
	}

	public void setApproveAt(Timestamp approveAt) {
		this.approveAt = approveAt;
	}

	public Timestamp getCompleteAt() {
		return completeAt;
	}

	public void setCompleteAt(Timestamp completeAt) {
		this.completeAt = completeAt;
	}

	public Timestamp getCancelAt() {
		return cancelAt;
	}

	public void setCancelAt(Timestamp cancelAt) {
		this.cancelAt = cancelAt;
	}

	public String getReasonCancel() {
		return reasonCancel;
	}

	public void setReasonCancel(String reasonCancel) {
		this.reasonCancel = reasonCancel;
	}

	public UUID getUserCancel() {
		return userCancel;
	}

	public void setUserCancel(UUID userCancel) {
		this.userCancel = userCancel;
	}

	public Timestamp getRejectAt() {
		return rejectAt;
	}

	public void setRejectAt(Timestamp rejectAt) {
		this.rejectAt = rejectAt;
	}

	public String getReasonReject() {
		return reasonReject;
	}

	public void setReasonReject(String reasonReject) {
		this.reasonReject = reasonReject;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public RequestDetailResponse(UUID id, UUID ownerId, UUID objectTableId, UUID helperId, String status,
			String requestType, UUID converstaionId, String languageCode, Timestamp approveAt, Timestamp completeAt,
			Timestamp cancelAt, String reasonCancel, UUID userCancel, Timestamp rejectAt, String reasonReject,
			Timestamp createdAt) {

		this.id = id;
		this.ownerId = ownerId;
		this.objectTableId = objectTableId;
		this.helperId = helperId;
		this.status = status;
		this.requestType = requestType;
		this.converstaionId = converstaionId;
		this.languageCode = languageCode;
		this.approveAt = approveAt;
		this.completeAt = completeAt;
		this.cancelAt = cancelAt;
		this.reasonCancel = reasonCancel;
		this.userCancel = userCancel;
		this.rejectAt = rejectAt;
		this.reasonReject = reasonReject;
		this.createdAt = createdAt;
	}

	public RequestDetailResponse() {

	}
    
}
