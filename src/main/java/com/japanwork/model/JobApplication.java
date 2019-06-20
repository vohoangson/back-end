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
@Table(name="job_application")
public class JobApplication {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;
    
    @OneToOne
    @JoinColumn(name = "job_id")
    private Job job;
    
    @OneToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
    
    @OneToOne
    @JoinColumn(name = "translator_id")
    private Translator translator;
    
    @Column(name="submit_application_at")
    private Timestamp submitApplicationAt;
    
    @Column(name="approve_application_at")
    private Timestamp approveApplicationAt;
    
    @Column(name="reject_application_at")
    private Timestamp rejectApplicationAt;
    
    @OneToOne
    @JoinColumn(name="candidate_support_conversaion_id")
    private Conversation candidateSupportConversaion;
    
    @OneToOne
    @JoinColumn(name="company_support_conversation_id")
    private Conversation companySupportConversation;
    
    @OneToOne
    @JoinColumn(name="all_conversation_id")
    private Conversation allConversation;
    
    @Column(name="application_succeed_at")
    private Timestamp applicationSucceedAt;
    
    @Column(name="cancel_reason")
    private String cancelReason;
    
    @Column(name="user_cancel")
    private int userCancel;
    
    @Column(name="cancel_at")
    private Timestamp cancelAt;
    
    @Column(name="status")
    private int status;
    
    @Column(name="create_date")
    private Timestamp createDate;
    
    @Column(name="update_date")
    private Timestamp updateDate;
    
    @Column(name="is_delete")
    private boolean isDelete;

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

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public JobApplication(UUID id, Job job, Candidate candidate, Translator translator, Timestamp submitApplicationAt,
			Timestamp approveApplicationAt, Timestamp rejectApplicationAt, Conversation candidateSupportConversaion,
			Conversation companySupportConversation, Conversation allConversation, Timestamp applicationSucceedAt,
			String cancelReason, int userCancel, Timestamp cancelAt, int status, Timestamp createDate,
			Timestamp updateDate, boolean isDelete) {
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
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.isDelete = isDelete;
	}

	public JobApplication() {
		super();
	}
}
