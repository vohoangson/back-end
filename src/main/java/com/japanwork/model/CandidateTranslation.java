package com.japanwork.model;

import com.japanwork.constant.EnumConstant.Gender;
import com.japanwork.constant.EnumConstant.Marital;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
public class CandidateTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @OneToOne
    @JoinColumn(name = "translator_id")
    private Translator translator;

    @OneToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @Column(name = "name", nullable = false, length = 128)
    private String fullName;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital")
    private Marital marital;

    @Column(name = "residental_address")
    private String residentalAddres;

    @Column(name = "introduction", length = 1000)
    private String introduction;

    @Column(name = "japanese_level")
    private int japaneseLevel;

    @Column(name = "expected_working_address")
    private String expectedWorkingAddress;

    @Column(name = "expected_salary")
    private float expectedSalary;

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

    public Translator getTranslator() {
        return translator;
    }

    public Language getLanguage() {
        return language;
    }

    public String getFullName() {
        return fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public Marital getMarital() {
        return marital;
    }

    public String getResidentalAddres() {
        return residentalAddres;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getJapaneseLevel() {
        return japaneseLevel;
    }

    public String getExpectedWorkingAddress() {
        return expectedWorkingAddress;
    }

    public float getExpectedSalary() {
        return expectedSalary;
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

    public void setTranslator(Translator translator) {
        this.translator = translator;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setMarital(Marital marital) {
        this.marital = marital;
    }

    public void setResidentalAddres(String residentalAddres) {
        this.residentalAddres = residentalAddres;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setJapaneseLevel(int japaneseLevel) {
        this.japaneseLevel = japaneseLevel;
    }

    public void setExpectedWorkingAddress(String expectedWorkingAddress) {
        this.expectedWorkingAddress = expectedWorkingAddress;
    }

    public void setExpectedSalary(float expectedSalary) {
        this.expectedSalary = expectedSalary;
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

    public CandidateTranslation(Candidate candidate, Translator translator, Language language, String fullName, Date dateOfBirth,
                                Gender gender, Marital marital, String residentalAddres, String introduction, int japaneseLevel,
                                String expectedWorkingAddress, float expectedSalary,
                                Timestamp createdAt, Timestamp updatedAt) {
        this.candidate = candidate;
        this.translator = translator;
        this.language = language;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.marital = marital;
        this.residentalAddres = residentalAddres;
        this.introduction = introduction;
        this.japaneseLevel = japaneseLevel;
        this.expectedWorkingAddress = expectedWorkingAddress;
        this.expectedSalary = expectedSalary;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CandidateTranslation() {

    }
}
