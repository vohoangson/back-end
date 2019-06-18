package com.japanwork.payload.request;

import java.sql.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CandidatePersonalRequest {	
	@NotBlank(message = "full_name_required")
	@JsonProperty("full_name")
	private String fullName;
	
	@NotNull(message = "date_of_birth_required")
	@JsonProperty("date_of_birth")
	private Date dateOfBirth;
	
	@NotBlank(message = "gender_required")
	private String gender;
	
	@NotBlank(message = "marital_required")
	private String marital;
	
	@NotNull(message = "residental_city_required")
	@JsonProperty("residental_city_id")
    private UUID residentalCityId;

	@NotNull(message = "residental_district_required")
	@JsonProperty("residental_district_id") 
    private UUID residentalDistrictId;
    
	@NotBlank(message = "residental_address_required")
	@JsonProperty("residental_address")
    private String residentalAddres;

	@NotBlank(message = "avatar_required")
    private String avatar;
    
    private String introduction;
    
    @JsonProperty("japanese_level")
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

	public String getResidentalAddres() {
		return residentalAddres;
	}

	public void setResidentalAddres(String residentalAddres) {
		this.residentalAddres = residentalAddres;
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
