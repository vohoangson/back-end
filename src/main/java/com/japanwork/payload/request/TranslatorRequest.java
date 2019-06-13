package com.japanwork.payload.request;

import java.sql.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.japanwork.model.City;
import com.japanwork.model.Country;
import com.japanwork.model.District;

public class TranslatorRequest {
	@NotBlank(message = "name_translator_required")
	private String name;
	
	@NotBlank(message = "gender_required")
	private String gender;
	
	@NotNull(message = "date_of_birth_required")
	@JsonProperty("date_of_birth")
	private Date dateOfBirth;
	
	@NotNull(message = "country_required")
	private Country country;
	@NotNull(message = "city_required")
	private City city;
	@NotNull(message = "district_required")
	private District district;
	@NotBlank(message = "address_required")
	private String address;
	private String introduction;
	@NotBlank(message = "avatar_required")
	private String avatar;
	
	@JsonProperty("japanese_level")
	@NotNull(message = "japanese_level_required")
	@Min(1)
	@Max(5)
	private int japaneseLevel;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public District getDistrict() {
		return district;
	}
	public void setDistrict(District district) {
		this.district = district;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getJapaneseLevel() {
		return japaneseLevel;
	}
	public void setJapaneseLevel(int japaneseLevel) {
		this.japaneseLevel = japaneseLevel;
	}
}
