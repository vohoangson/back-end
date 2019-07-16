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
import javax.persistence.Table;

@Entity
@Table(name="academy")
public class Academy {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
	private BigInteger id;
	
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="uid")
	private UUID uid;
	
	@Column(name="candidate_id")
	private UUID candidateId;
	
	@Column(name="academy_center_name")
	private String academyCenterName;
	
	@Column(name="major_name")
	private String majorName;
	
	@Column(name="grade")
	private float grade;
	
	@Column(name="grade_system")
	private int gradeSystem;
	
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

	public String getAcademyCenterName() {
		return academyCenterName;
	}

	public void setAcademyCenterName(String academyCenterName) {
		this.academyCenterName = academyCenterName;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

	public int getGradeSystem() {
		return gradeSystem;
	}

	public void setGradeSystem(int gradeSystem) {
		this.gradeSystem = gradeSystem;
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
	
	public Academy(UUID uid) {
		this.uid = uid;
	}

	public Academy(BigInteger id, UUID uid, UUID candidateId, String academyCenterName, String majorName, float grade,
			int gradeSystem, Date startDate, Date endDate, Timestamp createdAt, Timestamp updatedAt,
			Timestamp deletedAt) {
		super();
		this.id = id;
		this.uid = uid;
		this.candidateId = candidateId;
		this.academyCenterName = academyCenterName;
		this.majorName = majorName;
		this.grade = grade;
		this.gradeSystem = gradeSystem;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public Academy() {
	}
}
