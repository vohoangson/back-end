package com.japanwork.payload.request;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.japanwork.model.City;
import com.japanwork.model.Country;
import com.japanwork.model.District;

public class CandidatePersonalRequest {	
	@NotBlank(message = "\"full_name\":\"Full name is required!\"")
	@JsonProperty("full_name")
	private String fullName;
	
	@NotNull(message = "\"date_of_birth\":\"Date of birth is required!\"")
	@JsonProperty("date_of_birth")
	private Date dateOfBirth;
	
	@NotBlank(message = "\"gender\":\"Gender is required!\"")
	private String gender;
	
	@NotBlank(message = "\"marital\":\"Marital is required!\"")
	private String marital;
	
	@NotNull(message = "\"residental_country\":\"Country is required!\"")
	@JsonProperty("residental_country")
    private Country residentalCountry;
	
	@NotNull(message = "\"residental_city\":\"City is required!\"")
	@JsonProperty("residental_city")
    private City residentalCity;

	@NotNull(message = "\"residental_district\":\"District is required!\"")
	@JsonProperty("residental_district") 
    private District residentalDistrict;
    
	@NotBlank(message = "\"residental_address\":\"Addres is required!\"")
	@JsonProperty("residental_address")
    private String residentalAddres;

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
	
	public Country getResidentalCountry() {
		return residentalCountry;
	}

	public void setResidentalCountry(Country residentalCountry) {
		this.residentalCountry = residentalCountry;
	}

	public City getResidentalCity() {
		return residentalCity;
	}

	public void setResidentalCity(City residentalCity) {
		this.residentalCity = residentalCity;
	}

	public District getResidentalDistrict() {
		return residentalDistrict;
	}

	public void setResidentalDistrict(District residentalDistrict) {
		this.residentalDistrict = residentalDistrict;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}
