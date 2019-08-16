package com.japanwork.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class CandidateTranslationRequest {
    @NotNull(message = "language_required")
    @JsonProperty("language_id")
    private UUID languageId;

    @NotBlank(message = "name_not_blank")
    @JsonProperty("full_name")
    @Size(max = 128)
    private String fullName;

    @NotBlank(message = "residental_address_not_blank")
    @JsonProperty("residental_address")
    @Size(max = 255)
    private String residentalAddress;

    @NotBlank(message = "introduction_not_blank")
    @Size(max = 1000)
    private String introduction;

    @JsonProperty("expected_working_address")
    private String expectedWorkingAddress;

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
