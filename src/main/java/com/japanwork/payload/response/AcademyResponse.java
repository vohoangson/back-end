package com.japanwork.payload.response;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AcademyResponse {
	@JsonProperty("academy_center_name")
	private String academyCenterName;
	
	@JsonProperty("major_name")
	private String majorName;
	
	private float grade;
	
	@JsonProperty("grade_system")
	private int gradeSystem;
	
	@JsonProperty("start_date")
	private Date startDate;
	
	@JsonProperty("end_date")
	private Date endDate;

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

	public AcademyResponse(String academyCenterName, String majorName, float grade, int gradeSystem, Date startDate,
			Date endDate) {
		super();
		this.academyCenterName = academyCenterName;
		this.majorName = majorName;
		this.grade = grade;
		this.gradeSystem = gradeSystem;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public AcademyResponse() {
		super();
	}
}
