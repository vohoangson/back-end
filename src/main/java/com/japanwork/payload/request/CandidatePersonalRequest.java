package com.japanwork.payload.request;

import java.sql.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CandidatePersonalRequest {	
	@NotBlank
	@Size(max = 128)
	@JsonProperty("full_name")
	private String fullName;
	
	@NotNull
	@JsonProperty("date_of_birth")
	private Date dateOfBirth;
	
	@NotBlank
	private String gender;
	
	@NotBlank
	private String marital;
	
	@NotNull
	@JsonProperty("residental_city_id")
    private UUID residentalCityId;

	@NotNull
	@JsonProperty("residental_district_id")
    private UUID residentalDistrictId;
    
	@NotBlank
	@JsonProperty("residental_address")
    private String residentalAddress;

	@NotBlank
	@Size(max = 1000)
    private String avatar;
    
	@NotBlank
	@Size(max = 2000)
    private String introduction;
    
    @JsonProperty
    private int japaneseLevel;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMarital() {
		return marital;
	}

	public void setMarital(String marital) {
		this.marital = marital;
	}

	public UUID getResidentalCityId() {
		return residentalCityId;
	}

	public void setResidentalCityId(UUID residentalCityId) {
		this.residentalCityId = residentalCityId;
	}

	public UUID getResidentalDistrictId() {
		return residentalDistrictId;
	}

	public void setResidentalDistrictId(UUID residentalDistrictId) {
		this.residentalDistrictId = residentalDistrictId;
	}

	public String getResidentalAddress() {
		return residentalAddress;
	}

	public void setResidentalAddress(String residentalAddress) {
		this.residentalAddress = residentalAddress;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public int getJapaneseLevel() {
		return japaneseLevel;
	}

	public void setJapaneseLevel(int japaneseLevel) {
		this.japaneseLevel = japaneseLevel;
	}    
}
