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
@Table(name="job_application_status")
public class JobApplicationStatus {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;
	
	@Column(name = "creator_id")
	private UUID creatorId;
	
	@Column(name = "job_application_id")
	private UUID jobApplicationId;
	
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

	public UUID getJobApplicationId() {
		return jobApplicationId;
	}

	public void setJobApplicationId(UUID jobApplicationId) {
		this.jobApplicationId = jobApplicationId;
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

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public JobApplicationStatus(UUID id, UUID creatorId, UUID jobApplicationId, Translator translator,
			Candidate candidate, String reason, String status, Timestamp createdAt) {
		this.id = id;
		this.creatorId = creatorId;
		this.jobApplicationId = jobApplicationId;
		this.translator = translator;
		this.candidate = candidate;
		this.reason = reason;
		this.status = status;
		this.createdAt = createdAt;
	}

	public JobApplicationStatus() {
	}
}
