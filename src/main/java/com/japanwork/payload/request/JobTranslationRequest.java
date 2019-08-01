package com.japanwork.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class JobTranslationRequest {
    @NotNull(message = "job_required")
    @JsonProperty("job_id")
    private UUID jobId;

    @NotNull(message = "translator_required")
    @JsonProperty("translator_id")
    private UUID translatorId;

    @NotNull(message = "language_required")
    @JsonProperty("language_id")
    private UUID languageId;

    @NotBlank(message = "name_required")
    @Size(max = 128)
    private String name;

    private String address;

    @NotBlank(message = "description_blank")
    @Size(max = 2000)
    private String description;

    @JsonProperty("required_education")
    private String requiredEducation;

    @NotBlank(message = "required_experience_blank")
    @JsonProperty("required_experience")
    @Size(max = 255)
    private String requiredExperience;

    @NotBlank(message = "required_language_blank")
    @JsonProperty("required_language")
    @Size(max = 128)
    private String requiredLanguage;

    @Size(max = 1000)
    private String benefit;

    @JsonProperty("japanese_level_requirement")
    private int japaneseLevelRequirement;

    public UUID getJobId() {
        return jobId;
    }

    public UUID getTranslatorId() {
        return translatorId;
    }

    public UUID getLanguageId() {
        return languageId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getRequiredEducation() {
        return requiredEducation;
    }

    public String getRequired_experience() {
        return requiredExperience;
    }

    public String getRequiredLanguage() {
        return requiredLanguage;
    }

    public String getBenefit() {
        return benefit;
    }

    public int getJapaneseLevelRequirement() {
        return japaneseLevelRequirement;
    }

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
    }

    public void setTranslatorId(UUID translatorId) {
        this.translatorId = translatorId;
    }

    public void setLanguageId(UUID languageId) {
        this.languageId = languageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRequiredEducation(String requiredEducation) {
        this.requiredEducation = requiredEducation;
    }

    public void setRequired_experience(String requiredExperience) {
        this.requiredExperience = requiredExperience;
    }

    public void setRequiredLanguage(String requiredLanguage) {
        this.requiredLanguage = requiredLanguage;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public void setJapaneseLevelRequirement(int japaneseLevelRequirement) {
        this.japaneseLevelRequirement = japaneseLevelRequirement;
    }
}
