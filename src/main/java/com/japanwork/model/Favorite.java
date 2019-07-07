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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "favorite")
public class Favorite {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;
	
	@ManyToOne
    @JoinColumn(name = "candidate_id")
	private Candidate candidate;
	
	@ManyToOne
    @JoinColumn(name = "job_id")
	private Job job;
	
	@ManyToOne
    @JoinColumn(name = "company_id")
	private Company company;
	
	@ManyToOne
    @JoinColumn(name = "translator_id")
	private Translator translator;
	
	@Column(name = "favorite_type")
	private String favoriteType;
	
	@JsonIgnore
    @Column(name="create_at")
    private Timestamp createAt;
    
    @JsonIgnore
    @Column(name="is_delete")
    private boolean isDelete;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getFavoriteType() {
		return favoriteType;
	}

	public void setFavoriteType(String favoriteType) {
		this.favoriteType = favoriteType;
	}

	public Favorite(UUID id, Candidate candidate, Job job, Company company, Translator translator, String favoriteType,
			Timestamp createAt, boolean isDelete) {
		super();
		this.id = id;
		this.candidate = candidate;
		this.job = job;
		this.company = company;
		this.translator = translator;
		this.favoriteType = favoriteType;
		this.createAt = createAt;
		this.isDelete = isDelete;
	}

	public Favorite() {
		super();
	}
}
