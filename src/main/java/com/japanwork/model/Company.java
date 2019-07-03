package com.japanwork.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
@Table(name="company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;
    
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(name="name")
    private String name;
    
    @ManyToMany
    @JoinTable(name = "company_business", 
      joinColumns = { @JoinColumn(name = "company_id") }, 
      inverseJoinColumns = {@JoinColumn(name = "business_id") })
    private Set<Business> businesses = new HashSet<>();
    
    @Column(name="scale")
    private int scale;
    
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "district_id")   
    private District district;
    
    @Column(name="address")
    private String address;
    
    @JsonProperty("logo")
    @Column(name="logo_url")
    private String logoUrl;
    
    @JsonProperty("cover_image_url")
    @Column(name="cover_image_url")
    private String coverImageUrl;
    
    @Column(name="introduction")
    private String introduction;
    
    @Column(name="status")
    private String status;
    
    @JsonIgnore
    @Column(name="create_date")
    private Timestamp createDate;
    
    @JsonIgnore
    @Column(name="update_date")
    private Timestamp updateDate;
    
    @JsonIgnore
    @Column(name="is_delete")
    private boolean isDelete;

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

	public Set<Business> getBusinesses() {
		return businesses;
	}

	public void setBusinesses(Set<Business> businesses) {
		this.businesses = businesses;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
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

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	@JsonIgnore
	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	public Company() {
		super();
	}

	public Company(UUID id, User user, String name, Set<Business> businesses, int scale, City city,
			District district, String address, String logoUrl, String coverImageUrl, String introduction, String status,
			Timestamp createDate, Timestamp updateDate, boolean isDelete) {
		super();
		this.id = id;
		this.user = user;
		this.name = name;
		this.businesses = businesses;
		this.scale = scale;
		this.city = city;
		this.district = district;
		this.address = address;
		this.logoUrl = logoUrl;
		this.coverImageUrl = coverImageUrl;
		this.introduction = introduction;
		this.status = status;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.isDelete = isDelete;
	}
	
	public Company(UUID id) {
		super();
		this.id = id;

	}
}
