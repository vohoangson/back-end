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
@Table(name = "conversation")
public class Conversation {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;
	
	@OneToOne
    @JoinColumn(name = "company_id")
	private Company company;
	
	@OneToOne
    @JoinColumn(name = "candidate_id")
	private Candidate candidate;
	
	@OneToOne
    @JoinColumn(name = "translator_id")
	private Translator translator;
	
	@JoinColumn(name = "created_at")
	private Timestamp createdAt;
	
	@JoinColumn(name = "deleted_at")
	private Timestamp deletedAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

	public Conversation(UUID id, Company company, Candidate candidate, Translator translator, Timestamp createdAt,
			Timestamp deletedAt) {
		this.id = id;
		this.company = company;
		this.candidate = candidate;
		this.translator = translator;
		this.createdAt = createdAt;
		this.deletedAt = deletedAt;
	}

	public Conversation() {

	}	
}
