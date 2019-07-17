package com.japanwork.model;

import java.math.BigInteger;
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

@Entity
@Table(name="experience")
public class Experience {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
	private BigInteger id;
	
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="uid")
	private UUID uid;
	
    @Column(name = "candidate_id")
	private UUID candidateId;
	
	@Column(name="organizaion")
	private String organizaion;
	
	@Column(name="description")
	private String desc;
	
	@ManyToOne
    @JoinColumn(name = "level_id", referencedColumnName = "uid")
	private Level level;
	
	@ManyToOne
    @JoinColumn(name = "business_id", referencedColumnName = "uid")
	private Business business;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
    @Column(name="created_at")
    private Timestamp createdAt;
    
    @Column(name="updated_at")
    private Timestamp updatedAt;
    
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

	public UUID getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(UUID candidateId) {
		this.candidateId = candidateId;
	}

	public String getOrganizaion() {
		return organizaion;
	}

	public void setOrganizaion(String organizaion) {
		this.organizaion = organizaion;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public Experience(BigInteger id, UUID uid, UUID candidateId, String organizaion, String desc, Level level,
			Business business, Date startDate, Date endDate, Timestamp createdAt, Timestamp updatedAt,
			Timestamp deletedAt) {
		this.id = id;
		this.uid = uid;
		this.candidateId = candidateId;
		this.organizaion = organizaion;
		this.desc = desc;
		this.level = level;
		this.business = business;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}
	
	public Experience(UUID uid) {
		this.uid = uid;
	}

	public Experience() {
	}
}
