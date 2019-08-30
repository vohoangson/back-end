package com.japanwork.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class ExperienceTranslationRequest {
    @NotNull
    @JsonProperty("id")
    private UUID experienceId;

    @NotBlank
    @Size(max = 128)
    private String organization;

    @NotBlank
    @Size(max = 2000)
    private String description;

    public UUID getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(UUID experienceId) {
        this.experienceId = experienceId;
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
}
