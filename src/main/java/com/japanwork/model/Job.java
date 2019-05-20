package com.japanwork.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    
    @Column(name="company_id")
    private UUID companyId;
    
    @Column(name="contract_type_id")
    private UUID contractTypeId;
    
    @Column(name="business_type_id")
    private UUID businessTypeId;
    
    @Column(name="level_id")
    private UUID levelId;
    
    @Column(name="work_place_city_id")
    private UUID workPlaceCityId;
    
    @Column(name="work_place_district_id")
    private UUID workPlaceDistrictId;
    
    @Column(name="work_place_address")
    private String workPlaceAddress;
    
    @Column(name="description")
    private String description;
    
    @Column(name="skill_requirement")
    private String skillRequirement;
    
    @Column(name="benefit")
    private String benefit;
    
    @Column(name="japanese_level_requirement")
    private int japaneseLevelRequirement;
    
    @Column(name="min_salary")
    private float minSalary;
    
    @Column(name="max_salary")
    private float maxSalary;
    
    @Column(name="status")
    private int status;
    
    @Column(name="create_date")
    private Timestamp createDate;
    
    @Column(name="update_date")
    private Timestamp updateDate;
    
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

	public UUID getCompanyId() {
		return companyId;
	}

	public void setCompanyId(UUID companyId) {
		this.companyId = companyId;
	}

	public UUID getContractTypeId() {
		return contractTypeId;
	}

	public void setContractTypeId(UUID contractTypeId) {
		this.contractTypeId = contractTypeId;
	}
	
	public UUID getWorkPlaceCityId() {
		return workPlaceCityId;
	}
	
	public UUID getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(UUID businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public UUID getLevelId() {
		return levelId;
	}

	public void setLevelId(UUID levelId) {
		this.levelId = levelId;
	}

	public void setWorkPlaceCityId(UUID workPlaceCityId) {
		this.workPlaceCityId = workPlaceCityId;
	}

	public UUID getWorkPlaceDistrictId() {
		return workPlaceDistrictId;
	}

	public void setWorkPlaceDistrictId(UUID workPlaceDistrictId) {
		this.workPlaceDistrictId = workPlaceDistrictId;
	}

	public String getWorkPlaceAddress() {
		return workPlaceAddress;
	}

	public void setWorkPlaceAddress(String workPlaceAddress) {
		this.workPlaceAddress = workPlaceAddress;
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

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Job(UUID id, String name, UUID companyId, UUID contractTypeId, UUID businessTypeId, UUID levelId,
			UUID workPlaceCityId, UUID workPlaceDistrictId, String workPlaceAddress, String description,
			String skillRequirement, String benefit, int japaneseLevelRequirement, float minSalary, float maxSalary,
			int status, Timestamp createDate, Timestamp updateDate, boolean isDelete) {
		super();
		this.id = id;
		this.name = name;
		this.companyId = companyId;
		this.contractTypeId = contractTypeId;
		this.businessTypeId = businessTypeId;
		this.levelId = levelId;
		this.workPlaceCityId = workPlaceCityId;
		this.workPlaceDistrictId = workPlaceDistrictId;
		this.workPlaceAddress = workPlaceAddress;
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
