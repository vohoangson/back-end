package com.japanwork.payload.response;

import java.sql.Date;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CandidateResponse {
	private UUID id;
	
	@JsonProperty("full_name")
	private String fullName;
	
	@JsonProperty("date_of_birth")
	private Date dateOfBirth;
	
	private String gender;
	
	private String marital;
	
	@JsonProperty("residental_city_id")
    private UUID residentalCityId;

	@JsonProperty("residental_district_id")  
    private UUID residentalDistrictId;
    
	@JsonProperty("residental_address")
    private String residentalAddres;
    
    private String avatar;
    
    private String introduction;
    
    @JsonProperty("japanese_level")
    private int japaneseLevel;
    
    @JsonProperty("wish_working_city_id")
    private UUID wishWorkingCityId;

    @JsonProperty("wish_working_district_id") 
    private UUID wishWorkingDistrictId;
    
    @JsonProperty("wish_working_address")
    private String wishWorkingAddress;
    
    @JsonProperty("wish_business_id")
    private UUID wishBusinessId;
    
    @JsonProperty("wish_level_id")
    private UUID wishLevelId;
    
    @JsonProperty("wish_contract_id")
    private UUID wishContractId;

    @JsonProperty("wish_salary")
    private float wishSalary;
    
	private Set<AcademyResponse> academies;

	private Set<ExperienceResponse> experiences;
    
    @JsonProperty("language_certificates")
	private Set<LanguageCertificateResponse> languageCertificates;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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

	public Set<AcademyResponse> getAcademies() {
		return academies;
	}

	public void setAcademies(Set<AcademyResponse> academies) {
		this.academies = academies;
	}

	public Set<ExperienceResponse> getExperiences() {
		return experiences;
	}

	public void setExperiences(Set<ExperienceResponse> experiences) {
		this.experiences = experiences;
	}

	public Set<LanguageCertificateResponse> getLanguageCertificates() {
		return languageCertificates;
	}

	public void setLanguageCertificates(Set<LanguageCertificateResponse> languageCertificates) {
		this.languageCertificates = languageCertificates;
	}

	public CandidateResponse(UUID id, String fullName, Date dateOfBirth, String gender, String marital,
			UUID residentalCityId, UUID residentalDistrictId, String residentalAddres, String avatar,
			String introduction, int japaneseLevel, UUID wishWorkingCityId, UUID wishWorkingDistrictId,
			String wishWorkingAddress, UUID wishBusinessId, UUID wishLevelId, UUID wishContractId, float wishSalary,
			Set<AcademyResponse> academies, Set<ExperienceResponse> experiences,
			Set<LanguageCertificateResponse> languageCertificates) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.marital = marital;
		this.residentalCityId = residentalCityId;
		this.residentalDistrictId = residentalDistrictId;
		this.residentalAddres = residentalAddres;
		this.avatar = avatar;
		this.introduction = introduction;
		this.japaneseLevel = japaneseLevel;
		this.wishWorkingCityId = wishWorkingCityId;
		this.wishWorkingDistrictId = wishWorkingDistrictId;
		this.wishWorkingAddress = wishWorkingAddress;
		this.wishBusinessId = wishBusinessId;
		this.wishLevelId = wishLevelId;
		this.wishContractId = wishContractId;
		this.wishSalary = wishSalary;
		this.academies = academies;
		this.experiences = experiences;
		this.languageCertificates = languageCertificates;
	}

	public CandidateResponse() {
		super();
	}
}
