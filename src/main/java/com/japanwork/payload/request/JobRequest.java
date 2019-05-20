package com.japanwork.payload.request;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobRequest {
	private UUID id;
	private CompanyRequest company;
	private String name;
	private ContractRequest contract;
	private BusinessRequest business;
	private LevelRequest level;
	@JsonProperty("skill_requirement")
	private String skillRequirement;
	@JsonProperty("japanese_level")
	private int japaneseLevel;
	private String description;
	private CityRequest city;
	private DistrictRequest district;
	private String address;
	@JsonProperty("min_salary")
	private float minSalary;
	@JsonProperty("max_salary")
	private float maxSalary;
	private String benefit;
	private int status;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public CompanyRequest getCompany() {
		return company;
	}
	public void setCompany(CompanyRequest company) {
		this.company = company;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ContractRequest getContract() {
		return contract;
	}
	public void setContract(ContractRequest contract) {
		this.contract = contract;
	}
	public BusinessRequest getBusiness() {
		return business;
	}
	public void setBusiness(BusinessRequest business) {
		this.business = business;
	}
	public LevelRequest getLevel() {
		return level;
	}
	public void setLevel(LevelRequest level) {
		this.level = level;
	}
	public String getSkillRequirement() {
		return skillRequirement;
	}
	public void setSkillRequirement(String skillRequirement) {
		this.skillRequirement = skillRequirement;
	}
	public int getJapaneseLevel() {
		return japaneseLevel;
	}
	public void setJapaneseLevel(int japaneseLevel) {
		this.japaneseLevel = japaneseLevel;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public CityRequest getCity() {
		return city;
	}
	public void setCity(CityRequest city) {
		this.city = city;
	}
	public DistrictRequest getDistrict() {
		return district;
	}
	public void setDistrict(DistrictRequest district) {
		this.district = district;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getBenefit() {
		return benefit;
	}
	public void setBenefit(String benefit) {
		this.benefit = benefit;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
