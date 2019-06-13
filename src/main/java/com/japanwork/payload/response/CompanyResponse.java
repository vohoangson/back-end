package com.japanwork.payload.response;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyResponse {
	private String name;
	
	private int scale;
	
	@JsonProperty("business_ids")
	private Set<UUID> businessIds;
	
	@JsonProperty("city_id")
	private UUID cityId;
	
	@JsonProperty("district_id")
	private UUID districtId;
	
	private String address;
	
	private String logo;
	
	@JsonProperty("cover_image")
	private String coverImage;

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

	public Set<UUID> getBusinessIds() {
		return businessIds;
	}

	public void setBusinessIds(Set<UUID> businessIds) {
		this.businessIds = businessIds;
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

	public CompanyResponse(String name, int scale, Set<UUID> businessIds, UUID cityId, UUID districtId, String address,
			String logo, String coverImage) {
		super();
		this.name = name;
		this.scale = scale;
		this.businessIds = businessIds;
		this.cityId = cityId;
		this.districtId = districtId;
		this.address = address;
		this.logo = logo;
		this.coverImage = coverImage;
	}

	public CompanyResponse() {
		super();
	}
}
