package com.japanwork.model;

import org.hibernate.annotations.Where;

import com.querydsl.core.annotations.QueryEntity;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@QueryEntity
@Table(name="request_status")
public class RequestStatus {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;

	@Column(name = "creator_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
	private UUID creatorId;

	@ManyToOne
	@JoinColumn(name = "request_translation_id")
    @Where(clause = "deleted_at IS NULL")
	private RequestTranslation requestTranslation;

	@OneToOne
    @JoinColumn(name = "translator_id")
    @Where(clause = "deleted_at IS NULL")
	private Translator translator;

	@Column(name = "reason", length = 1000)
	private String reason;

	@Column(name = "status")
	private String status;

	@Column(name = "created_at")
	private Timestamp createdAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(UUID creatorId) {
		this.creatorId = creatorId;
	}

	public RequestTranslation getRequestTranslation() {
		return requestTranslation;
	}

	public void setRequestTranslation(RequestTranslation requestTranslation) {
		this.requestTranslation = requestTranslation;
	}

	public Translator getTranslator() {
		return translator;
	}

	public void setTranslator(Translator translator) {
		this.translator = translator;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public RequestStatus(UUID id, UUID creatorId, RequestTranslation requestTranslation, Translator translator,
			String reason, String status, Timestamp createdAt) {
		this.id = id;
		this.creatorId = creatorId;
		this.requestTranslation = requestTranslation;
		this.translator = translator;
		this.reason = reason;
		this.status = status;
		this.createdAt = createdAt;
	}

	public RequestStatus(UUID id) {
		this.id = id;
	}

	public RequestStatus() {
	}

}
