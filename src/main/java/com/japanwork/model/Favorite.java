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
	
    @Column(name="created_at")
    private Timestamp createdAt;
    
    @Column(name="deleted_at")
    private Timestamp deletedAt;
	
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
