package com.japanwork.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class JobTranslationRequest {
    @JsonProperty("language")
    private String language;

    @NotBlank
    @Size(max = 128)
    private String name;

    private String address;

    @NotBlank
    @Size(max = 2000)
    private String description;

    @JsonProperty("required_education")
    private String requiredEducation;

    @NotBlank
    @JsonProperty("required_experience")
    @Size(max = 255)
    private String requiredExperience;

    @NotBlank
    @JsonProperty("required_language")
    @Size(max = 128)
    private String requiredLanguage;

    @Size(max = 1000)
    private String benefit;

    @JsonProperty("japanese_level_requirement")
    private int japaneseLevelRequirement;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequiredEducation() {
        return requiredEducation;
    }

    public void setRequiredEducation(String requiredEducation) {
        this.requiredEducation = requiredEducation;
    }

    public String getRequiredExperience() {
        return requiredExperience;
    }

    public void setRequiredExperience(String requiredExperience) {
        this.requiredExperience = requiredExperience;
    }

    public String getRequiredLanguage() {
        return requiredLanguage;
    }

    public void setRequiredLanguage(String requiredLanguage) {
        this.requiredLanguage = requiredLanguage;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public int getJapaneseLevelRequirement() {
        return japaneseLevelRequirement;
    }

    public void setJapaneseLevelRequirement(int japaneseLevelRequirement) {
        this.japaneseLevelRequirement = japaneseLevelRequirement;
    }
}
