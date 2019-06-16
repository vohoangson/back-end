package com.japanwork.payload.response;

import java.sql.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TranslatorResponse {
	private UUID id;
	private String name;
	private String gender;
	@JsonProperty("date_of_birth")
	private Date dateOfBirth;
	@JsonProperty("city_id")
	private UUID cityId;
	@JsonProperty("district_id")
	private UUID districtId;
	private String address;
	private String introduction;
	private String avatar;
	@JsonProperty("japanese_level")
	private int japaneseLevel;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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

	public TranslatorResponse(UUID id, String name, String gender, Date dateOfBirth, UUID cityId, UUID districtId,
			String address, String introduction, String avatar, int japaneseLevel) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.cityId = cityId;
		this.districtId = districtId;
		this.address = address;
		this.introduction = introduction;
		this.avatar = avatar;
		this.japaneseLevel = japaneseLevel;
	}
}
