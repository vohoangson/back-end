package com.japanwork.payload.request;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.japanwork.model.LanguageCertificateType;

public class CandidateLangugeRequest {
private int score;
    
    @JsonProperty("language_certificate_type")
    private LanguageCertificateType languageCertificateType;
    
    @JsonProperty("taken_date")
    private Date takenDate;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public LanguageCertificateType getLanguageCertificateType() {
		return languageCertificateType;
	}

	public void setLanguageCertificateType(LanguageCertificateType languageCertificateType) {
		this.languageCertificateType = languageCertificateType;
	}

	public Date getTakenDate() {
		return takenDate;
	}

	public void setTakenDate(Date takenDate) {
		this.takenDate = takenDate;
	}
}
