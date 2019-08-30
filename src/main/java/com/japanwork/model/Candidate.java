package com.japanwork.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name="candidate")
@Where(clause = "deleted_at IS NULL")
public class Candidate {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;

	@OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @Where(clause = "deleted_at IS NULL")
	private User user;

	@Column(name="name", nullable = false, length = 128)
	private String fullName;

	@Column(name="dob", nullable = false)
	private Date dateOfBirth;

	@Column(name="gender", nullable = false)
	private String gender;

	@Column(name="marital", length = 64)
	private String marital;

	@ManyToOne
    @JoinColumn(name = "residental_city_id")
    @Where(clause = "deleted_at IS NULL")
    private City residentalCity;

    @ManyToOne
    @JoinColumn(name = "residental_district_id")
    @Where(clause = "deleted_at IS NULL")
    private District residentalDistrict;

    @Column(name="residental_address")
    private String residentalAddres;

    @Column(name="avatar_url")
    private String avatar;

    @Column(name="introduction")
    private String introduction;

    @Column(name="japanese_level")
    private int japaneseLevel;

    @ManyToOne
    @JoinColumn(name = "expected_working_city_id")
	@Where(clause = "deleted_at IS NULL")
    private City expectedWorkingCity;

    @ManyToOne
    @JoinColumn(name = "expected_working_district_id")
    @Where(clause = "deleted_at IS NULL")
    private District expectedWorkingDistrict;
    
    @Column(name="expected_working_address")
    private String expectedWorkingAddress;
    
    @ManyToOne
    @JoinColumn(name="expected_business_type_id")
    @Where(clause = "deleted_at IS NULL")
    private Business expectedBusiness;
    
    @ManyToOne
    @JoinColumn(name="expected_level_id")
    @Where(clause = "deleted_at IS NULL")
    private Level expectedLevel;
    
    @ManyToOne
    @JoinColumn(name="expected_contract_type_id")
    @Where(clause = "deleted_at IS NULL")
    private Contract expectedContract;

    @Column(name="expected_salary")
    private float expectedSalary;
    
    @OneToMany(orphanRemoval = true, mappedBy="candidate")
    @Where(clause = "deleted_at IS NULL")
	private Set<Academy> academies;

    @OneToMany(orphanRemoval = true, mappedBy="candidate")
    @Where(clause = "deleted_at IS NULL")
	private Set<Experience> experiences;

    @OneToMany(orphanRemoval = true, mappedBy="candidate")
    @Where(clause = "deleted_at IS NULL")
	private Set<LanguageCertificate> languageCertificates;

    @Column(name="status")
    private String status;

    @Column(name="status_info")
    private int statusInfo;

    @Column(name="created_at")
    private Timestamp createdAt;

    @Column(name="updated_at")
    private Timestamp updatedAt;

    @Column(name="deleted_at")
    private Timestamp deletedAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	public City getExpectedWorkingCity() {
		return expectedWorkingCity;
	}

	public void setExpectedWorkingCity(City expectedWorkingCity) {
		this.expectedWorkingCity = expectedWorkingCity;
	}

	public District getExpectedWorkingDistrict() {
		return expectedWorkingDistrict;
	}

	public void setExpectedWorkingDistrict(District expectedWorkingDistrict) {
		this.expectedWorkingDistrict = expectedWorkingDistrict;
	}

	public String getExpectedWorkingAddress() {
		return expectedWorkingAddress;
	}

	public void setExpectedWorkingAddress(String expectedWorkingAddress) {
		this.expectedWorkingAddress = expectedWorkingAddress;
	}

	public Business getExpectedBusiness() {
		return expectedBusiness;
	}

	public void setExpectedBusiness(Business expectedBusiness) {
		this.expectedBusiness = expectedBusiness;
	}

	public Level getExpectedLevel() {
		return expectedLevel;
	}

	public void setExpectedLevel(Level expectedLevel) {
		this.expectedLevel = expectedLevel;
	}

	public Contract getExpectedContract() {
		return expectedContract;
	}

	public void setExpectedContract(Contract expectedContract) {
		this.expectedContract = expectedContract;
	}

	public float getExpectedSalary() {
		return expectedSalary;
	}

	public void setExpectedSalary(float expectedSalary) {
		this.expectedSalary = expectedSalary;
	}

	public Set<Academy> getAcademies() {
		return academies;
	}

	public void setAcademies(Set<Academy> academies) {
		this.academies.clear();
		this.academies.addAll(academies);
	}

	public Set<Experience> getExperiences() {
		return experiences;
	}

	public void setExperiences(Set<Experience> experiences) {
		this.experiences.clear();
		this.experiences.addAll(experiences);
	}

	public Set<LanguageCertificate> getLanguageCertificates() {
		return languageCertificates;
	}

	public void setLanguageCertificates(Set<LanguageCertificate> languageCertificates) {
		this.languageCertificates.clear();
		this.languageCertificates.addAll(languageCertificates);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStatusInfo() {
		return statusInfo;
	}

	public void setStatusInfo(int statusInfo) {
		this.statusInfo = statusInfo;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

	public Candidate(UUID id, User user, String fullName, Date dateOfBirth, String gender, String marital,
			City residentalCity, District residentalDistrict, String residentalAddres, String avatar,
			String introduction, int japaneseLevel, City expectedWorkingCity, District expectedWorkingDistrict,
			String expectedWorkingAddress, Business expectedBusiness, Level expectedLevel, Contract expectedContract,
			float expectedSalary, Set<Academy> academies, Set<Experience> experiences,
			Set<LanguageCertificate> languageCertificates, String status, int statusInfo, Timestamp createdAt,
			Timestamp updatedAt, Timestamp deletedAt) {
		this.id = id;
		this.user = user;
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.marital = marital;
		this.residentalCity = residentalCity;
		this.residentalDistrict = residentalDistrict;
		this.residentalAddres = residentalAddres;
		this.avatar = avatar;
		this.introduction = introduction;
		this.japaneseLevel = japaneseLevel;
		this.expectedWorkingCity = expectedWorkingCity;
		this.expectedWorkingDistrict = expectedWorkingDistrict;
		this.expectedWorkingAddress = expectedWorkingAddress;
		this.expectedBusiness = expectedBusiness;
		this.expectedLevel = expectedLevel;
		this.expectedContract = expectedContract;
		this.expectedSalary = expectedSalary;
		this.academies = academies;
		this.experiences = experiences;
		this.languageCertificates = languageCertificates;
		this.status = status;
		this.statusInfo = statusInfo;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public Candidate(UUID id) {
		this.id = id;
	}

	public Candidate() {

	}
}
