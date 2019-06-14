package com.japanwork.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
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
@Table(name="experience")
public class Experience {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;
	
	@JsonIgnore
    @Column(name = "candidate_id")
	private UUID candidateId;
	
	@Column(name="organizaion")
	private String organizaion;
	
	@Column(name="description")
	private String description;
	
	@ManyToOne
    @JoinColumn(name = "level_id")
	private Level level;
	
	@ManyToOne
    @JoinColumn(name = "business_id")
	private Business business;
	
	@JsonProperty("start_date")
	@Column(name="start_date")
	private Date startDate;
	
	@JsonProperty("end_date")
	@Column(name="end_date")
	private Date endDate;
	
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

	public UUID getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(UUID candidateId) {
		this.candidateId = candidateId;
	}

	public String getOrganizaion() {
		return organizaion;
	}

	public void setOrganizaion(String organizaion) {
		this.organizaion = organizaion;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public Experience(UUID id, UUID candidateId, String organizaion, String description, Level level, Business business,
			Date startDate, Date endDate, Timestamp createDate, Timestamp updateDate, boolean isDelete) {
		super();
		this.id = id;
		this.candidateId = candidateId;
		this.organizaion = organizaion;
		this.description = description;
		this.level = level;
		this.business = business;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.isDelete = isDelete;
	}

	public Experience(UUID id) {
		super();
		this.id = id;
	}
	
	public Experience() {
		super();
	}
	
	public static Set<Experience> listExperience(Set<UUID> experienceIds){
		if(experienceIds != null) {
			Set<Experience> experiences = new HashSet<>();
			for (UUID id : experienceIds) {
				Experience academy = new Experience(id);
				experiences.add(academy);
			}
			return experiences;
		} else {
			return null;
		}
	}
	
	public static Set<UUID> listExperienceID(Set<Experience> experiences){
		if(experiences != null) {
			Set<UUID> experienceIds = new HashSet<>();
			for (Experience obj : experiences) {
				experienceIds.add(obj.getId());
			}
			return experienceIds;
		} else {
			return null;
		}
	}
}
