package com.japanwork.model;

import org.hibernate.annotations.Where;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.*;

@Entity
@Table(
        name="job_translation",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"job_id", "language_id"})}
)
public class JobTranslation {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
    private Job job;

    @OneToOne
    @JoinColumn(name = "language_id")
    @Where(clause = "deleted_at IS NULL")
    private Language language;

    @Column(name = "work_place_address")
    private String address;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "required_education", length = 2000)
    private String requiredEducation;

    @Column(name = "required_experience", nullable = false, length = 2000)
    private String requiredExperience;

    @Column(name = "benefit", length = 2000)
    private String benefit;

    @Column(name = "required_language", nullable = false, length = 1000)
    private String requiredLanguage;

    private int japaneseLevelRequirement;
    private int status;

    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

    public Job getJob() {
        return job;
    }

    public Language getLanguage() {
        return language;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public String getAddress() {
        return address;
    }

    public String getRequiredEducation() {
        return requiredEducation;
    }

    public String getRequiredExperience() {
        return requiredExperience;
    }

    public String getRequiredLanguage() {
        return requiredLanguage;
    }

    public int getJapaneseLevelRequirement() {
        return japaneseLevelRequirement;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRequiredEducation(String requiredEducation) {
        this.requiredEducation = requiredEducation;
    }

    public void setRequiredExperience(String requiredExperience) {
        this.requiredExperience = requiredExperience;
    }

    public void setRequiredLanguage(String requiredLanguage) {
        this.requiredLanguage = requiredLanguage;
    }

    public void setJapaneseLevelRequirement(int japaneseLevelRequirement) {
        this.japaneseLevelRequirement = japaneseLevelRequirement;
    }

    public String getBenefit() {
		return benefit;
	}

	public void setBenefit(String benefit) {
		this.benefit = benefit;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
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

    public JobTranslation(UUID id, Job job, Language language, String address, String name,
                          String description, String requiredEducation, String requiredExperience, String benefit,
                          String requiredLanguage, int japaneseLevelRequirement, int status, Timestamp createdAt,
                          Timestamp updatedAt, Timestamp deletedAt) {
        this.id = id;
        this.job = job;
        this.language = language;
        this.address = address;
        this.name = name;
        this.description = description;
        this.requiredEducation = requiredEducation;
        this.requiredExperience = requiredExperience;
        this.benefit = benefit;
        this.requiredLanguage = requiredLanguage;
        this.japaneseLevelRequirement = japaneseLevelRequirement;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public JobTranslation() {

	}
}
