package com.japanwork.payload.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobResponse {
	private UUID id;
	private CompanyResponse company;
	private String name;
	private ContractResponse contract;
	private BusinessResponse business;
	private LevelResponse level;
	@JsonProperty("skill_requirement")
	private String skillRequirement;
	@JsonProperty("japanese_level")
	private int japaneseLevel;
	private String description;
	private CityResponse city;
	private DistrictResponse district;
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

	public CompanyResponse getCompany() {
		return company;
	}

	public void setCompany(CompanyResponse company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ContractResponse getContract() {
		return contract;
	}

	public void setContract(ContractResponse contract) {
		this.contract = contract;
	}

	public BusinessResponse getBusiness() {
		return business;
	}

	public void setBusiness(BusinessResponse business) {
		this.business = business;
	}

	public LevelResponse getLevel() {
		return level;
	}

	public void setLevel(LevelResponse level) {
		this.level = level;
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

	public CityResponse getCity() {
		return city;
	}

	public void setCity(CityResponse city) {
		this.city = city;
	}

	public DistrictResponse getDistrict() {
		return district;
	}

	public void setDistrict(DistrictResponse district) {
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

	public String getSkillRequirement() {
		return skillRequirement;
	}

	public void setSkillRequirement(String skillRequirement) {
		this.skillRequirement = skillRequirement;
	}

	public JobResponse(UUID id, CompanyResponse company, String name, ContractResponse contract,
			BusinessResponse business, LevelResponse level, String skillRequirement, int japaneseLevel,
			String description, CityResponse city, DistrictResponse district, String address, float minSalary,
			float maxSalary, String benefit, int status) {
		super();
		this.id = id;
		this.company = company;
		this.name = name;
		this.contract = contract;
		this.business = business;
		this.level = level;
		this.skillRequirement = skillRequirement;
		this.japaneseLevel = japaneseLevel;
		this.description = description;
		this.city = city;
		this.district = district;
		this.address = address;
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
		this.benefit = benefit;
		this.status = status;
	}

	public JobResponse() {
		super();
	}
}
