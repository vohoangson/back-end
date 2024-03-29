package com.japanwork.payload.request;

import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
public class CompanyRequest {
	@NotBlank(message = "name_company_required")
	private String name;
	
	private int scale;
	
	@JsonProperty("business_ids")
	@NotNull(message = "businesses_required")
	private Set<UUID> businessIds;
	
	@JsonProperty("city_id")
	@NotNull(message = "city_required")
	private UUID cityId;
	
	@JsonProperty("district_id")
	@NotNull(message = "district_required")
	private UUID districtId;
	
	@NotBlank(message = "address_required")
	private String address;
	
	@NotBlank(message = "logo_required")
	private String logo;
	
	@JsonProperty("cover_image")
	@NotBlank(message = "cover_image_required")
	private String coverImage;
	
	private String introduction;

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

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
}
