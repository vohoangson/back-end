package com.japanwork.payload.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CandidateExperienceRequest {
	private List<AcademyRequest> academies;
	private List<ExperienceRequest> experiences;
	@JsonProperty("language_certificates")
	private List<LanguageCertificateRequest> languageCertificates;
	public List<AcademyRequest> getAcademies() {
		return academies;
	}
	public void setAcademies(List<AcademyRequest> academies) {
		this.academies = academies;
	}
	public List<ExperienceRequest> getExperiences() {
		return experiences;
	}
	public void setExperiences(List<ExperienceRequest> experiences) {
		this.experiences = experiences;
	}
	public List<LanguageCertificateRequest> getLanguageCertificates() {
		return languageCertificates;
	}
	public void setLanguageCertificates(List<LanguageCertificateRequest> languageCertificates) {
		this.languageCertificates = languageCertificates;
	}
}
