package com.japanwork.model;

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
@Table(name="history_status")
public class HistoryStatus {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;
	
	@Column(name = "user_create_id")
	private UUID userCreateId;
	
	@ManyToOne
    @JoinColumn(name = "objecttable_id")
	private RequestTranslation requestTranslation;
	
	@OneToOne
    @JoinColumn(name = "translator_id")
	private Translator translator;
	
	@OneToOne
    @JoinColumn(name = "candidate_id")
	private Candidate candidate;
	
	@Column(name = "reason")
	private String reason;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public UUID getUserCreateId() {
		return userCreateId;
	}

	public void setUserCreateId(UUID userCreateId) {
		this.userCreateId = userCreateId;
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

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
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

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public HistoryStatus(UUID id, UUID userCreateId, RequestTranslation requestTranslation, Translator translator,
			Candidate candidate, String reason, String status, String type, Timestamp createdAt) {
		super();
		this.id = id;
		this.userCreateId = userCreateId;
		this.requestTranslation = requestTranslation;
		this.translator = translator;
		this.candidate = candidate;
		this.reason = reason;
		this.status = status;
		this.type = type;
		this.createdAt = createdAt;
	}

	public HistoryStatus(UUID id) {
		this.id = id;
	}

	public HistoryStatus() {
	}
	
}
