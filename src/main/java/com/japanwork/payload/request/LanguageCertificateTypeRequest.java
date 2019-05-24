package com.japanwork.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LanguageCertificateTypeRequest {
	@JsonProperty("name_vi")
	private String nameVi;
	@JsonProperty("name_ja")
	private String nameJa;
	private String description;
	
	public String getNameVi() {
		return nameVi;
	}
	public void setNameVi(String nameVi) {
		this.nameVi = nameVi;
	}
	public String getNameJa() {
		return nameJa;
	}
	public void setNameJa(String nameJa) {
		this.nameJa = nameJa;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
