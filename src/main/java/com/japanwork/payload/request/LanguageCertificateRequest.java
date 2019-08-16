package com.japanwork.payload.request;

import java.sql.Date;
import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LanguageCertificateRequest {
	@NotNull
	@Min(0)
    private int score;
    
	@NotNull
    @JsonProperty("language_certificate_type_id")
    private UUID languageCertificateTypeId;
    
    @NotNull
    @JsonProperty("taken_date")
    private Date takenDate;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public UUID getLanguageCertificateTypeId() {
		return languageCertificateTypeId;
	}

	public void setLanguageCertificateTypeId(UUID languageCertificateTypeId) {
		this.languageCertificateTypeId = languageCertificateTypeId;
	}

	public Date getTakenDate() {
		return takenDate;
	}

	public void setTakenDate(Date takenDate) {
		this.takenDate = takenDate;
	}
}
