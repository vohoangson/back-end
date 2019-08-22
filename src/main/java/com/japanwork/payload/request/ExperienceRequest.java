package com.japanwork.payload.request;

import java.sql.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExperienceRequest {
	@NotBlank
	@Size(max = 128)
	private String organizaion;
	
	@NotBlank
	@Size(max = 1000)
	private String desc;
	
	@NotNull
	@JsonProperty("level_id")
	private UUID levelId;
	
	@NotNull
    @JsonProperty("business_id")
	private UUID businessId;
	
	@NotNull
	@JsonProperty("start_date")
	private Date startDate;
	
	@NotNull
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
}
