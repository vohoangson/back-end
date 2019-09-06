package com.japanwork.payload.request;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CandidateExpectedRequest {	
	@NotNull
    @JsonProperty("expected_working_country_id")  
    private UUID expectedWorkingCountryId;
	
	@NotNull
	@JsonProperty("expected_working_city_id")
    private UUID expectedWorkingCityId;
	
	@NotNull
	@JsonProperty("expected_working_district_id")
    private UUID expectedWorkingDistrictId;
    
    @NotNull
    @JsonProperty("expected_business_id")
    private UUID expectedBusinessId;
    
    @NotNull
    @JsonProperty("expected_level_id")
    private UUID expectedLevelId;
    
    @NotNull
    @JsonProperty("expected_contract_id")
    private UUID expectedContractId;

    @NotNull
    @JsonProperty("expected_salary")
    private float expectedSalary;

	public UUID getExpectedWorkingCountryId() {
		return expectedWorkingCountryId;
	}

	public void setExpectedWorkingCountryId(UUID expectedWorkingCountryId) {
		this.expectedWorkingCountryId = expectedWorkingCountryId;
	}

	public UUID getExpectedWorkingCityId() {
		return expectedWorkingCityId;
	}

	public void setExpectedWorkingCityId(UUID expectedWorkingCityId) {
		this.expectedWorkingCityId = expectedWorkingCityId;
	}

	public UUID getExpectedWorkingDistrictId() {
		return expectedWorkingDistrictId;
	}

	public void setExpectedWorkingDistrictId(UUID expectedWorkingDistrictId) {
		this.expectedWorkingDistrictId = expectedWorkingDistrictId;
	}

	public UUID getExpectedBusinessId() {
		return expectedBusinessId;
	}

	public void setExpectedBusinessId(UUID expectedBusinessId) {
		this.expectedBusinessId = expectedBusinessId;
	}

	public UUID getExpectedLevelId() {
		return expectedLevelId;
	}

	public void setExpectedLevelId(UUID expectedLevelId) {
		this.expectedLevelId = expectedLevelId;
	}

	public UUID getExpectedContractId() {
		return expectedContractId;
	}

	public void setExpectedContractId(UUID expectedContractId) {
		this.expectedContractId = expectedContractId;
	}

	public float getExpectedSalary() {
		return expectedSalary;
	}

	public void setExpectedSalary(float expectedSalary) {
		this.expectedSalary = expectedSalary;
	}
}
