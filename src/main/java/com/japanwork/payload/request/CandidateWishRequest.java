package com.japanwork.payload.request;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CandidateWishRequest {	
	@NotNull(message = "wish_working_city_required")
	@JsonProperty("wish_working_city_id")
    private UUID wishWorkingCityId;

	@NotNull(message = "wish_working_district_required")
    @JsonProperty("wish_working_district_id")  
    private UUID wishWorkingDistrictId;
    
	@NotBlank(message = "wish_working_address_required")
    @JsonProperty("wish_working_address")
    private String wishWorkingAddress;
    
    @NotNull(message = "wish_business_required")
    @JsonProperty("wish_business_id")
    private UUID wishBusinessId;
    
    @NotNull(message = "wish_level_required")
    @JsonProperty("wish_level_id")
    private UUID wishLevelId;
    
    @NotNull(message = "wish_contract_required")
    @JsonProperty("wish_contract_id")
    private UUID wishContractId;

    @NotNull(message = "wish_salary_required")
    @JsonProperty("wish_salary")
    private float wishSalary;

	public UUID getWishWorkingCityId() {
		return wishWorkingCityId;
	}

	public void setWishWorkingCityId(UUID wishWorkingCityId) {
		this.wishWorkingCityId = wishWorkingCityId;
	}

	public UUID getWishWorkingDistrictId() {
		return wishWorkingDistrictId;
	}

	public void setWishWorkingDistrictId(UUID wishWorkingDistrictId) {
		this.wishWorkingDistrictId = wishWorkingDistrictId;
	}

	public String getWishWorkingAddress() {
		return wishWorkingAddress;
	}

	public void setWishWorkingAddress(String wishWorkingAddress) {
		this.wishWorkingAddress = wishWorkingAddress;
	}

	public UUID getWishBusinessId() {
		return wishBusinessId;
	}

	public void setWishBusinessId(UUID wishBusinessId) {
		this.wishBusinessId = wishBusinessId;
	}

	public UUID getWishLevelId() {
		return wishLevelId;
	}

	public void setWishLevelId(UUID wishLevelId) {
		this.wishLevelId = wishLevelId;
	}

	public UUID getWishContractId() {
		return wishContractId;
	}

	public void setWishContractId(UUID wishContractId) {
		this.wishContractId = wishContractId;
	}

	public float getWishSalary() {
		return wishSalary;
	}

	public void setWishSalary(float wishSalary) {
		this.wishSalary = wishSalary;
	}
}
