package com.japanwork.payload.request;

import java.sql.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AcademyRequest {
	@NotBlank
	@Size(max = 128)
	@JsonProperty("academy_center_name")
	private String academyCenterName;
	
	@NotBlank
	@Size(max = 128)
	@JsonProperty("major_name")
	private String majorName;
	
	@NotNull
	@Min(value = 0)
	private float grade;
	
	@JsonProperty("grade_system")
	@Min(value = 0)
	private int gradeSystem;
	
	@NotNull
	@JsonProperty("start_date")
	private Date startDate;
	
	@NotNull
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
	
	
}
