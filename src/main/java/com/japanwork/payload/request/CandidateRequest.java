package com.japanwork.payload.request;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.japanwork.model.Business;
import com.japanwork.model.City;
import com.japanwork.model.District;
import com.japanwork.model.LanguageCertificate;
import com.japanwork.model.Level;

public class CandidateRequest {	
	@JsonProperty("full_name")
	private String fullName;
	
	private String gender;
	
	@JsonProperty("residental_city")
    private City residentalCity;

	@JsonProperty("residental_district") 
    private District residentalDistrict;
    
	@JsonProperty("residental_address")
    private String residentalAddres;

    private String avatar;
    
    private String introduction;
    
	@JsonProperty("japanese_level")
    private int japaneseLevel;
    
	@JsonProperty("language_certificate")
    private LanguageCertificate languageCertificate;
    
	@JsonProperty("wish_working_city")
    private City wishWorkingCity;

	@JsonProperty("wish_working_district")  
    private District wishWorkingDistrict;
    
	@JsonProperty("wish_working_address")
    private String wishWorkingAddress;
    
    @JsonProperty("wish_business")
    private Business wishBusiness;
    
    @JsonProperty("wish_level")
    private Level wishLevel;
    
    @JsonProperty("wish_contract")
    private Level wishContract;

    @JsonProperty("wish_salary")
    @Column(name="wish_salary")
    private String wishSalary;
    
    @Column(name="status")
    private int status;

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

	public LanguageCertificate getLanguageCertificate() {
		return languageCertificate;
	}

	public void setLanguageCertificate(LanguageCertificate languageCertificate) {
		this.languageCertificate = languageCertificate;
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

	public Level getWishContract() {
		return wishContract;
	}

	public void setWishContract(Level wishContract) {
		this.wishContract = wishContract;
	}

	public String getWishSalary() {
		return wishSalary;
	}

	public void setWishSalary(String wishSalary) {
		this.wishSalary = wishSalary;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
