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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    private String description;
    
    @JsonProperty("skill_requirement")
    @Column(name="skill_requirement")
    private String skillRequirement;
    
    @Column(name="benefit")
    private String benefit;
    
    @JsonProperty("japanese_level_requirement")
    @Column(name="japanese_level_requirement")
    private int japaneseLevelRequirement;
    
    @JsonProperty("min_salary")
    @Column(name="min_salary")
    private float minSalary;
    
    @JsonProperty("max_salary")
    @Column(name="max_salary")
    private float maxSalary;
    
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSkillRequirement() {
		return skillRequirement;
	}

	public void setSkillRequirement(String skillRequirement) {
		this.skillRequirement = skillRequirement;
	}

	public String getBenefit() {
		return benefit;
	}

	public void setBenefit(String benefit) {
		this.benefit = benefit;
	}

	public int getJapaneseLevelRequirement() {
		return japaneseLevelRequirement;
	}

	public void setJapaneseLevelRequirement(int japaneseLevelRequirement) {
		this.japaneseLevelRequirement = japaneseLevelRequirement;
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

	public Job(UUID id, String name, Company company, Contract contract, Level level, City city, District district,
			String address, String description, String skillRequirement, String benefit, int japaneseLevelRequirement,
			float minSalary, float maxSalary, int status, Timestamp createDate, Timestamp updateDate,
			boolean isDelete) {
		super();
		this.id = id;
		this.name = name;
		this.company = company;
		this.contract = contract;
		this.level = level;
		this.city = city;
		this.district = district;
		this.address = address;
		this.description = description;
		this.skillRequirement = skillRequirement;
		this.benefit = benefit;
		this.japaneseLevelRequirement = japaneseLevelRequirement;
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
		this.status = status;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.isDelete = isDelete;
	}

	public Job() {
		super();
	}
}
