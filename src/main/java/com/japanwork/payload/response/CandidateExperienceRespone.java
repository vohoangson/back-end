package com.japanwork.payload.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.japanwork.model.Academy;
import com.japanwork.model.Experience;
import com.japanwork.model.LanguageCertificate;

public class CandidateExperienceRespone {
	private List<Academy> academies;
	private List<Experience> experiences;
	@JsonProperty("language_certificates")
	private List<LanguageCertificate> languageCertificates;
	public List<Academy> getAcademies() {
		return academies;
	}
	public void setAcademies(List<Academy> academies) {
		this.academies = academies;
	}
	public List<Experience> getExperiences() {
		return experiences;
	}
	public void setExperiences(List<Experience> experiences) {
		this.experiences = experiences;
	}
	public List<LanguageCertificate> getLanguageCertificates() {
		return languageCertificates;
	}
	public void setLanguageCertificates(List<LanguageCertificate> languageCertificates) {
		this.languageCertificates = languageCertificates;
	}
	public CandidateExperienceRespone(List<Academy> academies, List<Experience> experiences,
			List<LanguageCertificate> languageCertificates) {
		super();
		this.academies = academies;
		this.experiences = experiences;
		this.languageCertificates = languageCertificates;
	}
	public CandidateExperienceRespone() {
		super();
	}
}
