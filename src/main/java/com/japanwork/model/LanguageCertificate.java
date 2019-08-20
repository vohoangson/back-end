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
import org.hibernate.annotations.Where;

@Entity
@Table(name="language_certificate")
@Where(clause = "deleted_at IS NULL")
public class LanguageCertificate {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

	@Column(name = "candidate_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
	private UUID candidateId;

    @Column(name="score")
    private int score;

    @ManyToOne
    @JoinColumn(name = "language_certificate_type_id")
    @Where(clause = "deleted_at IS NULL")
    private LanguageCertificateType languageCertificateType;

    @Column(name="taken_date", nullable = false)
    private Date takenDate;

    @JsonIgnore
    @Column(name="created_at")
    private Timestamp createdAt;

    @JsonIgnore
    @Column(name="updated_at")
    private Timestamp updatedAt;

    @JsonIgnore
    @Column(name="deleted_at")
    private Timestamp deletedAt;

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

	public LanguageCertificate(UUID id, UUID candidateId, int score, LanguageCertificateType languageCertificateType,
			Date takenDate, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
		this.id = id;
		this.candidateId = candidateId;
		this.score = score;
		this.languageCertificateType = languageCertificateType;
		this.takenDate = takenDate;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public LanguageCertificate(UUID id) {
		this.id = id;
	}

	public LanguageCertificate() {

	}
}
