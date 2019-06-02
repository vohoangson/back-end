package com.japanwork.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.japanwork.model.Business;
import com.japanwork.model.City;
import com.japanwork.model.Contract;
import com.japanwork.model.District;
import com.japanwork.model.Level;

public class CandidateWishRequest {
	
	@NotNull(message = "\"wish_working_city\":\"City is required!\"")
	@JsonProperty("wish_working_city")
    private City wishWorkingCity;

	@NotNull(message = "\"wish_working_district\":\"District is required!\"")
    @JsonProperty("wish_working_district")  
    private District wishWorkingDistrict;
    
	@NotBlank(message = "\"wish_working_address\":\"Address is required!\"")
    @JsonProperty("wish_working_address")
    private String wishWorkingAddress;
    
    @NotNull(message = "\"wish_business\":\"Business is required!\"")
    @JsonProperty("wish_business")
    private Business wishBusiness;
    
    @NotNull(message = "\"wish_level\":\"Level is required!\"")
    @JsonProperty("wish_level")
    private Level wishLevel;
    
    @NotNull(message = "\"wish_contract\":\"Contract is required!\"")
    @JsonProperty("wish_contract")
    private Contract wishContract;

    @NotNull(message = "\"wish_salary\":\"Salary is required!\"")
    @JsonProperty("wish_salary")
    private float wishSalary;

	public City getWishWorkingCity() {
		return wishWorkingCity;
	}

	public void setWishWorkingCity(City wishWorkingCity) {
		this.wishWorkingCity = wishWorkingCity;
	}

	public District getWishWorkingDistrict() {
		return wishWorkingDistrict;
	}

	public void setWishWorkingDistrict(District wishWorkingDistrict) {
		this.wishWorkingDistrict = wishWorkingDistrict;
	}

	public String getWishWorkingAddress() {
		return wishWorkingAddress;
	}

	public void setWishWorkingAddress(String wishWorkingAddress) {
		this.wishWorkingAddress = wishWorkingAddress;
	}

	public Business getWishBusiness() {
		return wishBusiness;
	}

	public void setWishBusiness(Business wishBusiness) {
		this.wishBusiness = wishBusiness;
	}

	public Level getWishLevel() {
		return wishLevel;
	}

	public void setWishLevel(Level wishLevel) {
		this.wishLevel = wishLevel;
	}

	public Contract getWishContract() {
		return wishContract;
	}

	public void setWishContract(Contract wishContract) {
		this.wishContract = wishContract;
	}

	public float getWishSalary() {
		return wishSalary;
	}

	public void setWishSalary(float wishSalary) {
		this.wishSalary = wishSalary;
	}
}
