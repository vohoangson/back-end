package com.japanwork.payload.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyResponse {
	private UUID id;
	private String name;
	private int scale;
	private BusinessResponse businesses;
	private CityResponse city;
	private DistrictResponse district;
	private String address;
	private String logo;
	@JsonProperty("cover_image")
	private String coverImage;
	private String introduction;
	@JsonProperty("is_publised")
	private int isPublised;
	
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

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public BusinessResponse getBusinesses() {
		return businesses;
	}

	public void setBusinesses(BusinessResponse businesses) {
		this.businesses = businesses;
	}

	public CityResponse getCity() {
		return city;
	}

	public void setCity(CityResponse city) {
		this.city = city;
	}

	public DistrictResponse getDistrict() {
		return district;
	}

	public void setDistrict(DistrictResponse district) {
		this.district = district;
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

	public int getIsPublised() {
		return isPublised;
	}

	public void setIsPublised(int isPublised) {
		this.isPublised = isPublised;
	}

	public CompanyResponse(UUID id, String name, int scale, BusinessResponse businesses, CityResponse city,
			DistrictResponse district, String address, String logo, String coverImage, String introduction,
			int isPublised) {
		super();
		this.id = id;
		this.name = name;
		this.scale = scale;
		this.businesses = businesses;
		this.city = city;
		this.district = district;
		this.address = address;
		this.logo = logo;
		this.coverImage = coverImage;
		this.introduction = introduction;
		this.isPublised = isPublised;
	}

	public CompanyResponse() {
		super();
	}
}
