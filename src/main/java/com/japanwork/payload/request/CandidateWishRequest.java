package com.japanwork.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.japanwork.model.Business;
import com.japanwork.model.City;
import com.japanwork.model.Contract;
import com.japanwork.model.Country;
import com.japanwork.model.District;
import com.japanwork.model.Level;

public class CandidateWishRequest {
	
	@NotNull(message = "wish_working_country_required")
	@JsonProperty("wish_working_country")
    private Country wishWorkingCountry;
	
	@NotNull(message = "wish_working_city_required")
	@JsonProperty("wish_working_city")
    private City wishWorkingCity;

	@NotNull(message = "wish_working_district_required")
    @JsonProperty("wish_working_district")  
    private District wishWorkingDistrict;
    
	@NotBlank(message = "wish_working_address_required")
    @JsonProperty("wish_working_address")
    private String wishWorkingAddress;
    
    @NotNull(message = "wish_business_required")
    @JsonProperty("wish_business")
    private Business wishBusiness;
    
    @NotNull(message = "wish_level_required")
    @JsonProperty("wish_level")
    private Level wishLevel;
    
    @NotNull(message = "wish_contract_required")
    @JsonProperty("wish_contract")
    private Contract wishContract;

    @NotNull(message = "wish_salary_required")
    @JsonProperty("wish_salary")
    private float wishSalary;
    
	public Country getWishWorkingCountry() {
		return wishWorkingCountry;
	}

	public void setWishWorkingCountry(Country wishWorkingCountry) {
		this.wishWorkingCountry = wishWorkingCountry;
	}

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
