package com.japanwork.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class AcademyTranslationRequest {
    @NotNull
    @JsonProperty("academy_id")
    private UUID academyId;

    @NotBlank
    @Size(max = 128)
    @JsonProperty("academy_center_name")
    private String academyCenterName;

    @NotBlank
    @Size(max = 128)
    @JsonProperty("major_name")
    private String majorName;

    public UUID getAcademyId() {
        return academyId;
    }

    public void setAcademyId(UUID academyId) {
        this.academyId = academyId;
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
}
