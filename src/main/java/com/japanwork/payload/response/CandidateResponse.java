package com.japanwork.payload.response;

import java.sql.Date;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
    
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("japanese_level")
    private int japaneseLevel;
    
    @JsonProperty("expected_working_city_id")
    private UUID expectedWorkingCityId;

    @JsonProperty("expected_working_district_id") 
    private UUID expectedWorkingDistrictId;
    
    @JsonProperty("expected_working_address")
    private String expectedWorkingAddress;
    
    @JsonProperty("expected_business_id")
    private UUID expectedBusinessId;
    
    @JsonProperty("expected_level_id")
    private UUID expectedLevelId;
    
    @JsonProperty("expected_contract_id")
    private UUID expectedContractId;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("expected_salary")
    private float expectedSalary;
    
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

	public String getExpectedWorkingAddress() {
		return expectedWorkingAddress;
	}

	public void setExpectedWorkingAddress(String expectedWorkingAddress) {
		this.expectedWorkingAddress = expectedWorkingAddress;
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
			String introduction, int japaneseLevel, UUID expectedWorkingCityId, UUID expectedWorkingDistrictId,
			String expectedWorkingAddress, UUID expectedBusinessId, UUID expectedLevelId, UUID expectedContractId,
			float expectedSalary, Set<AcademyResponse> academies, Set<ExperienceResponse> experiences,
			Set<LanguageCertificateResponse> languageCertificates) {
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
		this.expectedWorkingCityId = expectedWorkingCityId;
		this.expectedWorkingDistrictId = expectedWorkingDistrictId;
		this.expectedWorkingAddress = expectedWorkingAddress;
		this.expectedBusinessId = expectedBusinessId;
		this.expectedLevelId = expectedLevelId;
		this.expectedContractId = expectedContractId;
		this.expectedSalary = expectedSalary;
		this.academies = academies;
		this.experiences = experiences;
		this.languageCertificates = languageCertificates;
	}

	public CandidateResponse() {

	}
}
