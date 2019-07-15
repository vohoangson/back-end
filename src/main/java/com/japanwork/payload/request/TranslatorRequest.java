package com.japanwork.payload.request;

import java.sql.Date;
import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TranslatorRequest {
	@NotBlank(message = "name_translator_required")
	private String name;
	
	@NotBlank(message = "gender_required")
	private String gender;
	
	@NotNull(message = "date_of_birth_required")
	@JsonProperty("date_of_birth")
	private Date dateOfBirth;
	
	@NotNull(message = "city_required")
	@JsonProperty("city_id")
	private UUID cityId;
	@NotNull(message = "district_required")
	@JsonProperty("district_id")
	private UUID districtId;
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
	public UUID getCityId() {
		return cityId;
	}
	public void setCityId(UUID cityId) {
		this.cityId = cityId;
	}
	public UUID getDistrictId() {
		return districtId;
	}
	public void setDistrictId(UUID districtId) {
		this.districtId = districtId;
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
