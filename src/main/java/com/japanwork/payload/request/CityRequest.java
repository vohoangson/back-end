package com.japanwork.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CityRequest {
	@JsonProperty("country_code")
	private String countryCode;
	private String vi;
	private String ja;
	private String desc;
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getVi() {
		return vi;
	}
	public void setVi(String vi) {
		this.vi = vi;
	}
	public String getJa() {
		return ja;
	}
	public void setJa(String ja) {
		this.ja = ja;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
