package com.japanwork.payload.response;

import java.sql.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LanguageCertificateResponse {
    private int score;
    
    @JsonProperty("language_certificate_type_id")
    private UUID languageCertificateTypeId;
    
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

	public LanguageCertificateResponse(int score, UUID languageCertificateTypeId, Date takenDate) {
		super();
		this.score = score;
		this.languageCertificateTypeId = languageCertificateTypeId;
		this.takenDate = takenDate;
	}

	public LanguageCertificateResponse() {
		super();
	}
}
