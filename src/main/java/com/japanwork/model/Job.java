package com.japanwork.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="job")
public class Job {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;

	@Column(name="name")
    private String name;

	@ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

	@ManyToOne
	@JoinColumn(name = "business_id")
    private Business businesses;

	@ManyToOne
    @JoinColumn(name = "contract_type_id")
    private Contract contract;

	@ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

	@ManyToOne
    @JoinColumn(name = "work_place_city_id")
    private City city;

	@ManyToOne
    @JoinColumn(name = "work_place_district_id")
    private District district;

    @Column(name="work_place_address")
    private String address;

    @Column(name="description")
    private String desc;

    @Column(name="required_education")
    private String requiredEducation;

    @Column(name="required_experience")
    private String requiredExperience;

    @Column(name="required_language")
    private String requiredLanguage;

    @Column(name="benefits")
    private String benefits;

    @Column(name="japanese_level_requirement")
    private int japaneseLevelRequirement;

    @Column(name="application_deadline")
    private Date applicationDeadline;

    @Column(name="min_salary")
    private float minSalary;

    @Column(name="max_salary")
    private float maxSalary;

    @Column(name="status")
    private String status;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Business getBusinesses() {
		return businesses;
	}

	public void setBusinesses(Business businesses) {
		this.businesses = businesses;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRequiredEducation() {
		return requiredEducation;
	}

	public void setRequiredEducation(String requiredEducation) {
		this.requiredEducation = requiredEducation;
	}

	public String getRequiredExperience() {
		return requiredExperience;
	}

	public void setRequiredExperience(String requiredExperience) {
		this.requiredExperience = requiredExperience;
	}

	public String getRequiredLanguage() {
		return requiredLanguage;
	}

	public void setRequiredLanguage(String requiredLanguage) {
		this.requiredLanguage = requiredLanguage;
	}

	public String getBenefits() {
		return benefits;
	}

	public void setBenefits(String benefits) {
		this.benefits = benefits;
	}

	public int getJapaneseLevelRequirement() {
		return japaneseLevelRequirement;
	}

	public void setJapaneseLevelRequirement(int japaneseLevelRequirement) {
		this.japaneseLevelRequirement = japaneseLevelRequirement;
	}

	public Date getApplicationDeadline() {
		return applicationDeadline;
	}

	public void setApplicationDeadline(Date applicationDeadline) {
		this.applicationDeadline = applicationDeadline;
	}

	public float getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(float minSalary) {
		this.minSalary = minSalary;
	}

	public float getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(float maxSalary) {
		this.maxSalary = maxSalary;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Job(UUID id, String name, Company company, Business businesses, Contract contract, Level level, City city,
			District district, String address, String desc, String requiredEducation, String requiredExperience,
			String requiredLanguage, String benefits, int japaneseLevelRequirement, Date applicationDeadline,
			float minSalary, float maxSalary, String status, Timestamp createdAt, Timestamp updatedAt,
			Timestamp deletedAt) {
		this.id = id;
		this.name = name;
		this.company = company;
		this.businesses = businesses;
		this.contract = contract;
		this.level = level;
		this.city = city;
		this.district = district;
		this.address = address;
		this.desc = desc;
		this.requiredEducation = requiredEducation;
		this.requiredExperience = requiredExperience;
		this.requiredLanguage = requiredLanguage;
		this.benefits = benefits;
		this.japaneseLevelRequirement = japaneseLevelRequirement;
		this.applicationDeadline = applicationDeadline;
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public Job(UUID id) {
	    this.id = id;
    }

	public Job() {

	}
}
