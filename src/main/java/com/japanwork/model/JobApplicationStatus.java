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
@Table(name="job_application_status")
public class JobApplicationStatus {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;
	
	@Column(name = "creator_id")
	private UUID creatorId;
	
	@ManyToOne
	@JoinColumn(name = "job_application_id")
	private JobApplication jobApplication;
	
	@OneToOne
    @JoinColumn(name = "translator_id")
	private Translator translator;
	
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

	public JobApplication getJobApplication() {
		return jobApplication;
	}

	public void setJobApplication(JobApplication jobApplication) {
		this.jobApplication = jobApplication;
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

	public JobApplicationStatus(UUID id, UUID creatorId, JobApplication jobApplication, Translator translator,
			String reason, String status, Timestamp createdAt) {
		this.id = id;
		this.creatorId = creatorId;
		this.jobApplication = jobApplication;
		this.translator = translator;
		this.reason = reason;
		this.status = status;
		this.createdAt = createdAt;
	}

	public JobApplicationStatus() {
	}
}
