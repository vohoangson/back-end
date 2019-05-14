package com.japanwork.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;
    @Column(name="user_id")
    private UUID userId;
    @Column(name="name")
    private String name;
    @Column(name="bussiness_type_id")
    private UUID bussinessTypeId;
    @Column(name="scale")
    private int scale;
    @Column(name="city_id")
    private UUID cityId;
    @Column(name="district_id")
    private UUID districtId;
    @Column(name="address")
    private String address;
    @Column(name="logo_url")
    private String logoUrl;
    @Column(name="cover_image_url")
    private String coverImageUrl;
    @Column(name="introduction")
    private String introduction;
    @Column(name="is_publised")
    private int isPublised;
    @Column(name="create_date")
    private Timestamp createDate;
    @Column(name="update_date")
    private Timestamp updateDate;
    @Column(name="is_delete")
    private boolean isDelete;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
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
	public UUID getBussinessTypeId() {
		return bussinessTypeId;
	}
	public void setBussinessTypeId(UUID bussinessTypeId) {
		this.bussinessTypeId = bussinessTypeId;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
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
	public int getIsPublised() {
		return isPublised;
	}
	public void setIsPublised(int isPublised) {
		this.isPublised = isPublised;
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
	
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	public Company(UUID id, UUID userId, String name, UUID bussinessTypeId, int scale, UUID cityId, UUID districtId,
			String address, String logoUrl, String coverImageUrl, String introduction, int isPublised,
			Timestamp createDate, Timestamp updateDate, boolean isDelete) {
		super();
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.bussinessTypeId = bussinessTypeId;
		this.scale = scale;
		this.cityId = cityId;
		this.districtId = districtId;
		this.address = address;
		this.logoUrl = logoUrl;
		this.coverImageUrl = coverImageUrl;
		this.introduction = introduction;
		this.isPublised = isPublised;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.isDelete = isDelete;
	}
	public Company() {
		super();
	}
}
