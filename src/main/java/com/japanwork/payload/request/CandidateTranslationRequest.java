package com.japanwork.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.japanwork.constant.EnumConstant.Gender;
import com.japanwork.constant.EnumConstant.Marital;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

public class CandidateTranslationRequest {
    @NotNull(message = "translator_required")
    @JsonProperty("translator_id")
    private UUID translatorId;

    @NotNull(message = "language_required")
    @JsonProperty("language_id")
    private UUID languageId;

    @NotBlank(message = "name_not_blank")
    @JsonProperty("full_name")
    @Size(max = 128)
    private String fullName;

    @NotNull(message = "date_of_birth_not_blank")
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @NotNull
    private Gender gender;

    private Marital marital;

    @NotBlank(message = "residental_address_not_blank")
    @JsonProperty("residental_address")
    @Size(max = 255)
    private String residentalAddress;

    @NotBlank(message = "introduction_not_blank")
    @Size(max = 1000)
    private String introduction;

    @NotNull(message = "japanese_level_not_blank")
    @JsonProperty("japanese_level")
    private int japaneseLevel;

    @JsonProperty("expected_working_address")
    private String expectedWorkingAddress;

    @JsonProperty("expected_salary")
    private float expectedSalary;

    public UUID getTranslatorId() {
        return translatorId;
    }

    public UUID getLanguageId() {
        return languageId;
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

    public String getResidentalAddress() {
        return residentalAddress;
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

    public void setTranslatorId(UUID translatorId) {
        this.translatorId = translatorId;
    }

    public void setLanguageId(UUID languageId) {
        this.languageId = languageId;
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

    public void setResidentalAddress(String residentalAddress) {
        this.residentalAddress = residentalAddress;
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
}
