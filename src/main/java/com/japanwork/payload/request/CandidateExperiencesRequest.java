package com.japanwork.payload.request;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.japanwork.model.Business;
import com.japanwork.model.Level;

public class CandidateExperiencesRequest {
	private String organizaion;
	
	private String description;
	
	private Level level;
	
	private Business business;
	
	@JsonProperty("start_date")
	private Date startDate;
	
	@JsonProperty("end_date")
	private Date endDate;

	public String getOrganizaion() {
		return organizaion;
	}

	public void setOrganizaion(String organizaion) {
		this.organizaion = organizaion;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
}
