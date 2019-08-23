package com.japanwork.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public class CandidateTranslationRequest {
    @NotNull
    @JsonProperty("language_id")
    private UUID languageId;

    @JsonProperty("academy_translations")
    private List<AcademyTranslationRequest> academyTranslationRequests;

    @JsonProperty("experience_translations")
    private List<ExperienceTranslationRequest> experienceTranslationRequests;

    @JsonProperty("full_name")
    @Size(max = 128)
    private String fullName;

    @NotBlank
    @JsonProperty("residental_address")
    @Size(max = 255)
    private String residentalAddress;

    @NotBlank
    @Size(max = 1000)
    private String introduction;

    @JsonProperty("expected_working_address")
    private String expectedWorkingAddress;

    public List<AcademyTranslationRequest> getAcademyTranslationRequests() {
        return academyTranslationRequests;
    }

    public void setAcademyTranslationRequests(List<AcademyTranslationRequest> academyTranslationRequests) {
        this.academyTranslationRequests = academyTranslationRequests;
    }

    public List<ExperienceTranslationRequest> getExperienceTranslationRequests() {
        return experienceTranslationRequests;
    }

    public void setExperienceTranslationRequests(List<ExperienceTranslationRequest> experienceTranslationRequests) {
        this.experienceTranslationRequests = experienceTranslationRequests;
    }

    public UUID getLanguageId() {
        return languageId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getResidentalAddress() {
        return residentalAddress;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getExpectedWorkingAddress() {
        return expectedWorkingAddress;
    }

    public void setLanguageId(UUID languageId) {
        this.languageId = languageId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setResidentalAddress(String residentalAddress) {
        this.residentalAddress = residentalAddress;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setExpectedWorkingAddress(String expectedWorkingAddress) {
        this.expectedWorkingAddress = expectedWorkingAddress;
    }
}
