package com.japanwork.payload.request;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.japanwork.model.Business;
import com.japanwork.model.City;
import com.japanwork.model.Company;
import com.japanwork.model.Contract;
import com.japanwork.model.Country;
import com.japanwork.model.District;
import com.japanwork.model.Level;

public class JobRequest {
	@NotNull(message = "company_required")
	private Company company;
	
	
	@NotBlank(message = "name_job_required")
	private String name;
	
	@NotNull(message = "businesses_required")
	private Set<Business> businesses = new HashSet<>();
	
	@NotNull(message = "contract_required")
	private Contract contract;
	
	@NotNull(message = "level_required")
	private Level level;
	
	@JsonProperty("japanese_level")
	private int japaneseLevel;
	
	@JsonProperty("required_education")
	private String requiredEducation;
	
	
	@JsonProperty("required_experience")
	private String requiredExperience;
	
	@JsonProperty("required_language")
	private String requiredLanguage;
	
	private String desc;
	
	@NotNull(message = "country_required")
	private Country country;
	
	@NotNull(message = "city_required")
	private City city;
	
	@NotNull(message = "district_required")
	private District district;
	
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Business> getBusinesses() {
		return businesses;
	}

	public void setBusinesses(Set<Business> businesses) {
		this.businesses = businesses;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
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
