package com.japanwork.payload.request;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobFilterRequest {
	@JsonProperty("job_name")
	private String jobName;
	
	@JsonProperty("company_name")
	private String companyName;
	
	@JsonProperty("business_ids")
	private Set<UUID> businessIds = new HashSet<>();
	
	@JsonProperty("contract_ids")
	private Set<UUID> contractIds = new HashSet<>();
	
	@JsonProperty("level_ids")
	private Set<UUID> levelIds = new HashSet<>();
	
	@JsonProperty("city_ids")
	private Set<UUID> cityIds = new HashSet<>();
	
	@JsonProperty("min_salary")
	private int minSalary;
	
	@JsonProperty("post_time")
	private Date postTime;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Set<UUID> getBusinessIds() {
		return businessIds;
	}

	public void setBusinessIds(Set<UUID> businessIds) {
		this.businessIds = businessIds;
	}

	public Set<UUID> getContractIds() {
		return contractIds;
	}

	public void setContractIds(Set<UUID> contractIds) {
		this.contractIds = contractIds;
	}

	public Set<UUID> getLevelIds() {
		return levelIds;
	}

	public void setLevelIds(Set<UUID> levelIds) {
		this.levelIds = levelIds;
	}

	public Set<UUID> getCityIds() {
		return cityIds;
	}

	public void setCityIds(Set<UUID> cityIds) {
		this.cityIds = cityIds;
	}

	public int getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(int minSalary) {
		this.minSalary = minSalary;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
}
