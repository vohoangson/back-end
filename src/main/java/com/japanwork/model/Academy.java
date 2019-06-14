package com.japanwork.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="academy")
public class Academy {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;
	
	@JsonIgnore
	@Column(name="candidate_id")
	private UUID candidateId;
	
	@JsonProperty("academy_center_name")
	@Column(name="academy_center_name")
	private String academyCenterName;
	
	@JsonProperty("major_name")
	@Column(name="major_name")
	private String majorName;
	
	@Column(name="grade")
	private float grade;
	
	@JsonProperty("grade_system")
	@Column(name="grade_system")
	private int gradeSystem;
	
	@JsonProperty("start_date")
	@Column(name="start_date")
	private Date startDate;
	
	@JsonProperty("end_date")
	@Column(name="end_date")
	private Date endDate;
	
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

	public Academy(UUID id, UUID candidateId, String academyCenterName, String majorName, float grade, int gradeSystem,
			Date startDate, Date endDate, Timestamp createDate, Timestamp updateDate, boolean isDelete) {
		super();
		this.id = id;
		this.candidateId = candidateId;
		this.academyCenterName = academyCenterName;
		this.majorName = majorName;
		this.grade = grade;
		this.gradeSystem = gradeSystem;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.isDelete = isDelete;
	}

	public Academy(UUID id) {
		super();
		this.id = id;
	}
	
	public Academy() {
		super();
	}
}
