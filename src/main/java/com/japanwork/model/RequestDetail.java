package com.japanwork.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="request_detail")
public class RequestDetail {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;
    
	@Column(name = "owner_id")
	private UUID ownerId;
	
	@Column(name = "object_table_id")
    private UUID objectTableId;
    
    @OneToOne
    @JoinColumn(name = "helper_id")
    private Translator translator;
    
    @Column(name="status")
    private String status;
    
    @Column(name="request_type")
    private String requestType;
    
    @OneToOne
    @JoinColumn(name="conversation_id")
    private Conversation converstaion;
    
    @OneToOne
    @JoinColumn(name="language_id")
    private Language language;
    
    @Column(name="approve_at")
    private Timestamp approveAt;
    
    @Column(name="complete_at")
    private Timestamp completeAt;
    
    @Column(name="cancel_at")
    private Timestamp cancelAt;
    
    @Column(name="reason_cancel")
    private String reasonCancel;
    
    @Column(name="user_cancel")
    private UUID userCancel;
    
    @Column(name="reject_at")
    private Timestamp rejectAt;
    
    @Column(name="reason_reject")
    private String reasonReject;
    
    @Column(name="created_at")
    private Timestamp createdAt;
    
    @Column(name="updated_at")
    private Timestamp updatedAt;
    
    @Column(name="deleted_at")
    private Timestamp deletedAt;

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

	public Translator getTranslator() {
		return translator;
	}

	public void setTranslator(Translator translator) {
		this.translator = translator;
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

	public Conversation getConverstaion() {
		return converstaion;
	}

	public void setConverstaion(Conversation converstaion) {
		this.converstaion = converstaion;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
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

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

	public RequestDetail(UUID id, UUID ownerId, UUID objectTableId, Translator translator, String status,
			String requestType, Conversation converstaion, Language language, Timestamp approveAt, Timestamp completeAt,
			Timestamp cancelAt, String reasonCancel, UUID userCancel, Timestamp rejectAt, String reasonReject,
			Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
		super();
		this.id = id;
		this.ownerId = ownerId;
		this.objectTableId = objectTableId;
		this.translator = translator;
		this.status = status;
		this.requestType = requestType;
		this.converstaion = converstaion;
		this.language = language;
		this.approveAt = approveAt;
		this.completeAt = completeAt;
		this.cancelAt = cancelAt;
		this.reasonCancel = reasonCancel;
		this.userCancel = userCancel;
		this.rejectAt = rejectAt;
		this.reasonReject = reasonReject;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public RequestDetail() {

	}
}
