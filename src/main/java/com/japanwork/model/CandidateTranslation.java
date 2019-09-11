package com.japanwork.model;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.Where;

@Entity
@Table(name="candidate_translation",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"candidate_id", "language_id"})})
@Where(clause = "deleted_at IS NULL")
public class CandidateTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
    private Candidate candidate;

    @OneToOne
    @JoinColumn(name = "language_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
    private Language language;

    @Column(name = "name", nullable = false, length = 128)
    private String fullName;

    @Column(name = "residental_address")
    private String residentalAddres;

    @Column(name = "introduction")
    private String introduction;

    @OneToMany(orphanRemoval = true, mappedBy="candidateTranslation")
    @Where(clause = "deleted_at IS NULL")
    private Set<AcademyTranslation> academyTranslations;

    @OneToMany(orphanRemoval = true, mappedBy="candidateTranslation")
    @Where(clause = "deleted_at IS NULL")
    private Set<ExperienceTranslation> experienceTranslations;

    @Column(name="created_at")
    private Timestamp createdAt;

    @Column(name="updated_at")
    private Timestamp updatedAt;

    @Column(name="deleted_at")
    private Timestamp deletedAt;

    public UUID getId() {
        return id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public Language getLanguage() {
        return language;
    }

    public String getFullName() {
        return fullName;
    }

    public String getResidentalAddres() {
        return residentalAddres;
    }

    public String getIntroduction() {
        return introduction;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setResidentalAddres(String residentalAddres) {
        this.residentalAddres = residentalAddres;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Set<AcademyTranslation> getAcademyTranslations() {
        return academyTranslations;
    }

    public void setAcademyTranslations(Set<AcademyTranslation> academyTranslations) {
        this.academyTranslations = academyTranslations;
    }

    public Set<ExperienceTranslation> getExperienceTranslations() {
        return experienceTranslations;
    }

    public void setExperienceTranslations(Set<ExperienceTranslation> experienceTranslations) {
        this.experienceTranslations = experienceTranslations;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public CandidateTranslation(Candidate candidate, Language language, String fullName, String residentalAddres, String introduction,
                                Timestamp createdAt, Timestamp updatedAt) {
        this.candidate = candidate;
        this.language = language;
        this.fullName = fullName;
        this.residentalAddres = residentalAddres;
        this.introduction = introduction;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CandidateTranslation() {
    }
}
