package com.japanwork.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="language_certificate")
public class LanguageCertificate {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;
    
	@Column(name = "candidate_id")
	private UUID candidateId;
	
    @Column(name="score")
    private int score;
    
    @JsonProperty("language_certificate_type")
    @ManyToOne
    @JoinColumn(name = "language_certificate_type_id")
    private LanguageCertificateType languageCertificateType;
    
    @JsonProperty("taken_date")
    @Column(name="taken_date")
    private Date takenDate;
    
    @JsonIgnore
    @Column(name="create_date")
    private Timestamp createDate;
    
    @JsonIgnore
    @Column(name="update_date")
    private Timestamp updateDate;
    
    @JsonIgnore
    @Column(name="is_delete")
    private boolean isDelete;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(UUID candidateId) {
		this.candidateId = candidateId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public LanguageCertificateType getLanguageCertificateType() {
		return languageCertificateType;
	}

	public void setLanguageCertificateType(LanguageCertificateType languageCertificateType) {
		this.languageCertificateType = languageCertificateType;
	}

	public Date getTakenDate() {
		return takenDate;
	}

	public void setTakenDate(Date takenDate) {
		this.takenDate = takenDate;
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

	@JsonIgnore
	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public LanguageCertificate(UUID id, UUID candidateId, int score, LanguageCertificateType languageCertificateType,
			Date takenDate, Timestamp createDate, Timestamp updateDate, boolean isDelete) {
		super();
		this.id = id;
		this.candidateId = candidateId;
		this.score = score;
		this.languageCertificateType = languageCertificateType;
		this.takenDate = takenDate;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.isDelete = isDelete;
	}

	public LanguageCertificate() {
		super();
	}
}
