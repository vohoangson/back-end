package com.japanwork.payload.request;

import java.sql.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobRequest {	
	@NotBlank(message = "name_job_required")
	private String name;
	
	@NotNull(message = "businesses_required")
	@JsonProperty("business_id")
	private UUID businessId;
	
	@NotNull(message = "contract_required")
	@JsonProperty("contract_id")
	private UUID contractId;
	
	@NotNull(message = "level_required")
	@JsonProperty("level_id")
	private UUID levelId;
	
	@JsonProperty("japanese_level")
	private int japaneseLevel;
	
	@JsonProperty("required_education")
	private String requiredEducation;
	
	@JsonProperty("required_experience")
	private String requiredExperience;
	
	@JsonProperty("required_language")
	private String requiredLanguage;
	
	private String desc;
	
	@NotNull(message = "city_required")
	@JsonProperty("city_id")
	private UUID cityId;
	
	@NotNull(message = "district_required")
	@JsonProperty("district_id")
	private UUID districtId;
	
	@NotBlank(message = "address_required")
	private String address;
	
    @JsonProperty("expiring_date")
    private Date applicationDeadline;
    
	@JsonProperty("min_salary")
	private float minSalary;
	
	@JsonProperty("max_salary")
	private float maxSalary;
	
	@NotBlank(message = "benefits_required")
	private String benefits;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public UUID getBusinessId() {
		return businessId;
	}

	public void setBusinessId(UUID businessId) {
		this.businessId = businessId;
	}

	public UUID getContractId() {
		return contractId;
	}

	public void setContractId(UUID contractId) {
		this.contractId = contractId;
	}

	public UUID getLevelId() {
		return levelId;
	}

	public void setLevelId(UUID levelId) {
		this.levelId = levelId;
	}

	public int getJapaneseLevel() {
		return japaneseLevel;
	}

	public void setJapaneseLevel(int japaneseLevel) {
		this.japaneseLevel = japaneseLevel;
	}

	public String getRequiredEducation() {
		return requiredEducation;
	}

	public void setRequiredEducation(String requiredEducation) {
		this.requiredEducation = requiredEducation;
	}

	public String getRequiredExperience() {
		return requiredExperience;
	}

	public void setRequiredExperience(String requiredExperience) {
		this.requiredExperience = requiredExperience;
	}

	public String getRequiredLanguage() {
		return requiredLanguage;
	}

	public void setRequiredLanguage(String requiredLanguage) {
		this.requiredLanguage = requiredLanguage;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public UUID getCityId() {
		return cityId;
	}

	public void setCityId(UUID cityId) {
		this.cityId = cityId;
	}

	public UUID getDistrictId() {
		return districtId;
	}

	public void setDistrictId(UUID districtId) {
		this.districtId = districtId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getApplicationDeadline() {
		return applicationDeadline;
	}

	public void setApplicationDeadline(Date applicationDeadline) {
		this.applicationDeadline = applicationDeadline;
	}

	public float getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(float minSalary) {
		this.minSalary = minSalary;
	}

	public float getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(float maxSalary) {
		this.maxSalary = maxSalary;
	}

	public String getBenefits() {
		return benefits;
	}

	public void setBenefits(String benefits) {
		this.benefits = benefits;
	}
}
