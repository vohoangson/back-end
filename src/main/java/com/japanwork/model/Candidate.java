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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="candidate")
public class Candidate {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;
	
	@JsonIgnore
	@OneToOne
    @JoinColumn(name = "user_id")
	private User user;
	
	@Column(name="name")
	private String fullName;
	
	@JsonProperty("date_of_birth")
	@Column(name="dob")
	private Date dateOfBirth;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="marital")
	private String marital;
	
	@JsonProperty("residental_city")
	@ManyToOne
    @JoinColumn(name = "residental_city_id")
    private City residentalCity;

	@JsonProperty("residental_district")
    @ManyToOne
    @JoinColumn(name = "residental_district_id")   
    private District residentalDistrict;
    
	@JsonProperty("residental_address")
    @Column(name="residental_address")
    private String residentalAddres;
    
    @Column(name="avatar_url")
    private String avatar;
    
    @Column(name="introduction")
    private String introduction;
    
    @JsonProperty("japanese_level")
    @Column(name="japanese_level")
    private int japaneseLevel;
    
    @JsonProperty("wish_working_city")
    @ManyToOne
    @JoinColumn(name = "wish_working_city_id")
    private City wishWorkingCity;

    @JsonProperty("wish_working_district")
    @ManyToOne
    @JoinColumn(name = "wish_working_district_id")   
    private District wishWorkingDistrict;
    
    @JsonProperty("wish_working_address")
    @Column(name="wish_working_address")
    private String wishWorkingAddress;
    
    @JsonProperty("wish_business")
    @ManyToOne
    @JoinColumn(name="wish_business_type_id")
    private Business wishBusiness;
    
    @JsonProperty("wish_level")
    @ManyToOne
    @JoinColumn(name="wish_level_id")
    private Level wishLevel;
    
    @JsonProperty("wish_contract")
    @ManyToOne
    @JoinColumn(name="wish_contract_type_id")
    private Contract wishContract;

    @JsonProperty("wish_salary")
    @Column(name="wish_salary")
    private float wishSalary;
    
    @OneToMany
    @JoinColumn(name="candidate_id")
    @Where(clause = "is_delete = false")
	private Set<Academy> academies;

    @OneToMany
    @JoinColumn(name="candidate_id")
    @Where(clause = "is_delete = false")
	private Set<Experience> experiences;
    
    @OneToMany
    @JoinColumn(name="candidate_id")
    @Where(clause = "is_delete = false")
	private Set<LanguageCertificate> languageCertificates;
    
    @Column(name="status")
    private String status;

    @Column(name="status_info")
    private int statusInfo;
    
    @JsonIgnore
    @Column(name="create_date")
    private Timestamp createDate;
    
    @JsonIgnore
    @Column(name="update_date")
    private Timestamp updateDate;
    
    @JsonIgnore
    @Column(name="is_delete")
    private boolean isDelete;
    
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

	public Set<Academy> getAcademies() {
		return academies;
	}

	public void setAcademies(Set<Academy> academies) {
		this.academies = academies;
	}

	public Set<Experience> getExperiences() {
		return experiences;
	}

	public void setExperiences(Set<Experience> experiences) {
		this.experiences = experiences;
	}

	public Set<LanguageCertificate> getLanguageCertificates() {
		return languageCertificates;
	}

	public void setLanguageCertificates(Set<LanguageCertificate> languageCertificates) {
		this.languageCertificates = languageCertificates;
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

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	@JsonIgnore
	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Candidate(UUID id, User user, String fullName, Date dateOfBirth, String gender, String marital,
			City residentalCity, District residentalDistrict, String residentalAddres, String avatar,
			String introduction, int japaneseLevel, City wishWorkingCity,
			District wishWorkingDistrict, String wishWorkingAddress, Business wishBusiness, Level wishLevel,
			Contract wishContract, float wishSalary, Set<Academy> academies, Set<Experience> experiences,
			Set<LanguageCertificate> languageCertificates, String status, int statusInfo, Timestamp createDate,
			Timestamp updateDate, boolean isDelete) {
		super();
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
		this.wishWorkingCity = wishWorkingCity;
		this.wishWorkingDistrict = wishWorkingDistrict;
		this.wishWorkingAddress = wishWorkingAddress;
		this.wishBusiness = wishBusiness;
		this.wishLevel = wishLevel;
		this.wishContract = wishContract;
		this.wishSalary = wishSalary;
		this.academies = academies;
		this.experiences = experiences;
		this.languageCertificates = languageCertificates;
		this.status = status;
		this.statusInfo = statusInfo;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.isDelete = isDelete;
	}

	public Candidate() {
		super();
	}
}
