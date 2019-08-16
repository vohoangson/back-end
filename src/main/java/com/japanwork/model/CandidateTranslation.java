package com.japanwork.model;

import com.japanwork.constant.EnumConstant.Gender;
import com.japanwork.constant.EnumConstant.Marital;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(
        name="candidate_translation",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"candidate_id", "language_id"})}
)
@Where(clause = "deleted_at IS NULL")
public class CandidateTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
    private Candidate candidate;

//    @OneToOne
//    @JoinColumn(name = "translator_id")
//    @Where(clause = "deleted_at IS NULL")
//    private Translator translator;

    @OneToOne
    @JoinColumn(name = "language_id", nullable = false)
    @Where(clause = "deleted_at IS NULL")
    private Language language;

    @Column(name = "name", nullable = false, length = 128)
    private String fullName;

//    @Column(name = "date_of_birth", nullable = false)
//    private Date dateOfBirth;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "gender", nullable = false)
//    private Gender gender;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "marital")
//    private Marital marital;

    @Column(name = "residental_address")
    private String residentalAddres;

    @Column(name = "introduction", length = 1000)
    private String introduction;

//    @Column(name = "japanese_level")
//    private int japaneseLevel;

    @Column(name = "expected_working_address")
    private String expectedWorkingAddress;

//    @Column(name = "expected_salary")
//    private float expectedSalary;

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

    public String getExpectedWorkingAddress() {
        return expectedWorkingAddress;
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

    public void setExpectedWorkingAddress(String expectedWorkingAddress) {
        this.expectedWorkingAddress = expectedWorkingAddress;
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

    public CandidateTranslation(Candidate candidate, Language language, String fullName,
                                String residentalAddres, String introduction,
                                String expectedWorkingAddress, Timestamp createdAt, Timestamp updatedAt) {
        this.candidate = candidate;
        this.language = language;
        this.fullName = fullName;
        this.residentalAddres = residentalAddres;
        this.introduction = introduction;
        this.expectedWorkingAddress = expectedWorkingAddress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CandidateTranslation() {
    }
}
