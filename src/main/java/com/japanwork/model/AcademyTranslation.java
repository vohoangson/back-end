package com.japanwork.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(
        name = "academy_translation",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"academy_id", "language_id"})}
)
@Where(clause = "deleted_at IS NULL")
public class AcademyTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @Where(clause = "deleted_at IS NULL")
    private Academy academy;

    @ManyToOne
    @Where(clause = "deleted_at IS NULL")
    private CandidateTranslation candidateTranslation;

    @OneToOne
    @JoinColumn(name = "language_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
    private Language language;

    @Column(name = "academy_center_name", nullable = false, length = 128)
    private String academyCenterName;

    @Column(name = "majorName", nullable = false, length = 128)
    private String majorName;

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

    public Academy getAcademy() {
        return academy;
    }

    public void setAcademy(Academy academy) {
        this.academy = academy;
    }

    public CandidateTranslation getCandidateTranslation() {
        return candidateTranslation;
    }

    public void setCandidateTranslation(CandidateTranslation candidateTranslation) {
        this.candidateTranslation = candidateTranslation;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getAcademyCenterName() {
        return academyCenterName;
    }

    public void setAcademyCenterName(String academyCenterName) {
        this.academyCenterName = academyCenterName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
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
