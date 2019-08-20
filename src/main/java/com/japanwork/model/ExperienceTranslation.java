package com.japanwork.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(
        name = "experience_translation",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"experience_id", "language_id"})}
)
@Where(clause = "deleted_at IS NULL")
public class ExperienceTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @Column(name = "experience_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
    private UUID experienceId;

    @OneToOne
    @JoinColumn(name = "language_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
    private Language language;

    @Column(name = "organization", length = 128)
    private String organization;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(UUID experienceId) {
        this.experienceId = experienceId;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
