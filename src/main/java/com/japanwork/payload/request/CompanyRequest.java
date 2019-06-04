package com.japanwork.payload.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.japanwork.model.Business;
import com.japanwork.model.City;
import com.japanwork.model.Country;
import com.japanwork.model.District;
public class CompanyRequest {
	@NotBlank(message = "\"name\":\"Name is required!\"")
	private String name;
	private int scale;
	@NotNull(message = "\"businesses\":\"Businesses is required!\"")
	private Set<Business> businesses;
	@NotNull(message = "\"country\":\"Country is required!\"")
	private Country country;
	@NotNull(message = "\"city\":\"City is required!\"")
	private City city;
	@NotNull(message = "\"district\":\"District is required!\"")
	private District district;
	@NotBlank(message = "\"address\":\"Address is required!\"")
	private String address;
	private String logo;
	@JsonProperty("cover_image")
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
	public Set<Business> getBusinesses() {
		return businesses;
	}
	public void setBusinesses(Set<Business> businesses) {
		this.businesses = businesses;
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
