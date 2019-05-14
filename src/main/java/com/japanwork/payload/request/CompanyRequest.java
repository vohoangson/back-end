package com.japanwork.payload.request;

import java.util.UUID;

public class CompanyRequest {
	private UUID userId;
	private String name;
	private int scale;
	private UUID businessId;
	private UUID cityId;
	private UUID districtId;
	private String address;
	private String logo;
	private String coverImage;
	private String introduction;
	
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public UUID getBusinessId() {
		return businessId;
	}
	public void setBusinessId(UUID businessId) {
		this.businessId = businessId;
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
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getCoverImage() {
		return coverImage;
	}
	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
}
