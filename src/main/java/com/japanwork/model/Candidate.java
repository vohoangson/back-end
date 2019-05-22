package com.japanwork.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	
	@Column(name="")
	private String gender;
	
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
    
    @Column(name="living_url")
    private String avatar;
    
    @Column(name="introduction")
    private String introduction;
    
    @JsonProperty("japanese_level")
    @Column(name="japanese_level")
    private int japaneseLevel;
    
    @JsonProperty("language_certificate")
    @ManyToOne
    @JoinColumn(name = "language_certificate_id")
    private LanguageCertificate languageCertificate;
    
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
    private Level wishContract;

    @JsonProperty("wish_salary")
    @Column(name="wish_salary")
    private String wishSalary;
    
    @Column(name="status")
    private int status;

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

	public Candidate(UUID id, User user, String fullName, String gender, City residentalCity,
			District residentalDistrict, String residentalAddres, String avatar, String introduction, int japaneseLevel,
			LanguageCertificate languageCertificate, City wishWorkingCity, District wishWorkingDistrict,
			String wishWorkingAddress, Business wishBusiness, Level wishLevel, Level wishContract, String wishSalary,
			int status, Timestamp createDate, Timestamp updateDate, boolean isDelete) {
		super();
		this.id = id;
		this.user = user;
		this.fullName = fullName;
		this.gender = gender;
		this.residentalCity = residentalCity;
		this.residentalDistrict = residentalDistrict;
		this.residentalAddres = residentalAddres;
		this.avatar = avatar;
		this.introduction = introduction;
		this.japaneseLevel = japaneseLevel;
		this.languageCertificate = languageCertificate;
		this.wishWorkingCity = wishWorkingCity;
		this.wishWorkingDistrict = wishWorkingDistrict;
		this.wishWorkingAddress = wishWorkingAddress;
		this.wishBusiness = wishBusiness;
		this.wishLevel = wishLevel;
		this.wishContract = wishContract;
		this.wishSalary = wishSalary;
		this.status = status;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.isDelete = isDelete;
	}

	public Candidate() {
		super();
	}
}
