package com.japanwork.model;

import org.hibernate.annotations.Where;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="job_application")
@Where(clause = "deleted_at IS NULL")
public class JobApplication {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;

    @OneToOne
    @JoinColumn(name = "job_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
    private Job job;

    @OneToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
    private Candidate candidate;

    @OneToOne
    @JoinColumn(name = "translator_id")
    @Where(clause = "deleted_at IS NULL")
    private Translator translator;

    @OneToOne
    @JoinColumn(name="candidate_support_conversaion_id")
    @Where(clause = "deleted_at IS NULL")
    private Conversation candidateSupportConversaion;

    @OneToOne
    @JoinColumn(name="company_support_conversation_id")
    @Where(clause = "deleted_at IS NULL")
    private Conversation companySupportConversation;

    @OneToOne
    @JoinColumn(name="all_conversation_id")
    @Where(clause = "deleted_at IS NULL")
    private Conversation allConversation;

    @OneToMany(mappedBy = "jobApplication")
    @OrderBy("createdAt DESC")
    private Set<JobApplicationStatus> jobApplicationStatus;

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

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Translator getTranslator() {
		return translator;
	}

	public void setTranslator(Translator translator) {
		this.translator = translator;
	}

	public Conversation getCandidateSupportConversaion() {
		return candidateSupportConversaion;
	}

	public void setCandidateSupportConversaion(Conversation candidateSupportConversaion) {
		this.candidateSupportConversaion = candidateSupportConversaion;
	}

	public Conversation getCompanySupportConversation() {
		return companySupportConversation;
	}

	public void setCompanySupportConversation(Conversation companySupportConversation) {
		this.companySupportConversation = companySupportConversation;
	}

	public Conversation getAllConversation() {
		return allConversation;
	}

	public void setAllConversation(Conversation allConversation) {
		this.allConversation = allConversation;
	}

	public Set<JobApplicationStatus> getJobApplicationStatus() {
		return jobApplicationStatus;
	}

	public void setJobApplicationStatus(Set<JobApplicationStatus> jobApplicationStatus) {
		this.jobApplicationStatus = jobApplicationStatus;
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

	public JobApplication(UUID id, Job job, Candidate candidate, Translator translator,
			Conversation candidateSupportConversaion, Conversation companySupportConversation,
			Conversation allConversation, Set<JobApplicationStatus> jobApplicationStatus, Timestamp createdAt,
			Timestamp updatedAt, Timestamp deletedAt) {
		this.id = id;
		this.job = job;
		this.candidate = candidate;
		this.translator = translator;
		this.candidateSupportConversaion = candidateSupportConversaion;
		this.companySupportConversation = companySupportConversation;
		this.allConversation = allConversation;
		this.jobApplicationStatus = jobApplicationStatus;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public JobApplication() {

	}
}
