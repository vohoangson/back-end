package com.japanwork.payload.response;

import java.sql.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExperienceResponse {
	private String organizaion;
	
	private String desc;
	
	@JsonProperty("level_id")
	private UUID levelId;
	
    @JsonProperty("business_id")
	private UUID businessId;
	
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public UUID getLevelId() {
		return levelId;
	}

	public void setLevelId(UUID levelId) {
		this.levelId = levelId;
	}

	public UUID getBusinessId() {
		return businessId;
	}

	public void setBusinessId(UUID businessId) {
		this.businessId = businessId;
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

	public ExperienceResponse(String organizaion, String desc, UUID levelId, UUID businessId, Date startDate,
			Date endDate) {
		super();
		this.organizaion = organizaion;
		this.desc = desc;
		this.levelId = levelId;
		this.businessId = businessId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public ExperienceResponse() {
		super();
	}
}
