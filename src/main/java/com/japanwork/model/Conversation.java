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

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonIgnore
	private Timestamp createAt;
	
	@JsonIgnore
	private boolean isDelete;

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

	public Timestamp getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}
	
	@JsonIgnore
	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Conversation(UUID id, Company company, Candidate candidate, Translator translator, Timestamp createAt,
			boolean isDelete) {
		super();
		this.id = id;
		this.company = company;
		this.candidate = candidate;
		this.translator = translator;
		this.createAt = createAt;
		this.isDelete = isDelete;
	}

	public Conversation() {
		super();
	}	
}
