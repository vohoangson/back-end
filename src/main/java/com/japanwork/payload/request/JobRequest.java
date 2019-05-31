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
import com.japanwork.model.CurrencyUnit;
import com.japanwork.model.District;
import com.japanwork.model.Level;

public class JobRequest {
	@NotNull(message = "\"company\":\"Company is required!\"")
	private Company company;
	
	
	@NotBlank(message = "\"name\":\"Name is required!\"")
	private String name;
	
	@NotNull(message = "\"businesses\":\"Businesses is required!\"")
	private Set<Business> businesses = new HashSet<>();
	
	@NotNull(message = "\"contract\":\"Contract is required!\"")
	private Contract contract;
	
	@NotNull(message = "\"level\":\"Level is required!\"")
	private Level level;
	
	@NotBlank(message = "\"skill_requirement\":\"Skill requirement is required!\"")
	@JsonProperty("skill_requirement")
	private String skillRequirement;
	
	@JsonProperty("japanese_level")
	private int japaneseLevel;
	
	private String description;
	
	@NotNull(message = "\"city\":\"City is required!\"")
	private City city;
	
	@NotNull(message = "\"district\":\"District is required!\"")
	private District district;
	
	@NotBlank(message = "\"address\":\"Address is required!\"")
	private String address;
	
    @JsonProperty("application_deadline")
    private Date applicationDeadline;
    
    @JsonProperty("currency_unit")
    private CurrencyUnit currencyUnit;
    
	@JsonProperty("min_salary")
	private float minSalary;
	
	@JsonProperty("max_salary")
	private float maxSalary;
	
	@NotBlank(message = "\"benefit\":\"Benefit is required!\"")
	private String benefit;
	
	private String status;
	
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
	public Date getApplicationDeadline() {
		return applicationDeadline;
	}
	public void setApplicationDeadline(Date applicationDeadline) {
		this.applicationDeadline = applicationDeadline;
	}
	public CurrencyUnit getCurrencyUnit() {
		return currencyUnit;
	}
	public void setCurrencyUnit(CurrencyUnit currencyUnit) {
		this.currencyUnit = currencyUnit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
