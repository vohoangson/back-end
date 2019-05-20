package com.japanwork.payload.request;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.japanwork.model.Business;
import com.japanwork.model.City;
import com.japanwork.model.District;
import com.japanwork.model.User;
public class CompanyRequest {
	private UUID id;
	private User user;
	private String name;
	private int scale;
	private Business businesses;
	private City city;
	private District district;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public Business getBusinesses() {
		return businesses;
	}
	public void setBusinesses(Business businesses) {
		this.businesses = businesses;
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
}
