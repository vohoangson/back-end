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

	public boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Job(UUID id, String name, UUID companyId, UUID contractTypeId, UUID workPlaceDistrictId,
			String workPlaceAddress, String description, String skillRequirement, String benefit,
			int japaneseLevelRequirement, Timestamp createDate, Timestamp updateDate,
			boolean isDelete) {
		super();
		this.id = id;
		this.name = name;
		this.companyId = companyId;
		this.contractTypeId = contractTypeId;
		this.workPlaceDistrictId = workPlaceDistrictId;
		this.workPlaceAddress = workPlaceAddress;
		this.description = description;
		this.skillRequirement = skillRequirement;
		this.benefit = benefit;
		this.japaneseLevelRequirement = japaneseLevelRequirement;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.isDelete = isDelete;
	}

	public Job() {
		super();
	}    
}
