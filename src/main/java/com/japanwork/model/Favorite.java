package com.japanwork.model;

import java.math.BigInteger;
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

@Entity
@Table(name = "favorite")
public class Favorite {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
	private BigInteger id;
	
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="uid")
	private UUID uid;
	
	@ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "uid")
	private Candidate candidate;
	
	@ManyToOne
    @JoinColumn(name = "job_id", referencedColumnName = "uid")
	private Job job;
	
	@ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "uid")
	private Company company;
	
	@ManyToOne
    @JoinColumn(name = "translator_id", referencedColumnName = "uid")
	private Translator translator;
	
	@Column(name = "favorite_type")
	private String favoriteType;
	
    @Column(name="created_at")
    private Timestamp createdAt;
    
    @Column(name="deleted_at")
    private Timestamp deletedAt;
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public UUID getUid() {
		return uid;
	}

	public void setUid(UUID uid) {
		this.uid = uid;
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

	public String getFavoriteType() {
		return favoriteType;
	}

	public void setFavoriteType(String favoriteType) {
		this.favoriteType = favoriteType;
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

	public Favorite(BigInteger id, UUID uid, Candidate candidate, Job job, Company company, Translator translator,
			String favoriteType, Timestamp createdAt, Timestamp deletedAt) {
		this.id = id;
		this.uid = uid;
		this.candidate = candidate;
		this.job = job;
		this.company = company;
		this.translator = translator;
		this.favoriteType = favoriteType;
		this.createdAt = createdAt;
		this.deletedAt = deletedAt;
	}

	public Favorite() {
	}
}
