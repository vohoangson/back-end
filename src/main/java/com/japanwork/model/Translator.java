package com.japanwork.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="translator")
public class Translator {
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
    
    @Column(name="gender")
    private String gender;
    
    @JsonProperty("date_of_birth")
    @Column(name="dob")
    private Date dateOfBirth;
    
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "district_id")   
    private District district;
    
    @Column(name="address")
    private String address;
    
    @Column(name="introduction")
    private String introduction;
    
    @Column(name="avatar_url")
    private String avatar;
    
    @JsonProperty("japanese_level")
    @Column(name="japanese_level")
    private int japaneseLevel;
    
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

	public Translator() {
		super();
	}
	public Translator(UUID id, User user, String name, String gender, Date dateOfBirth, Country country, City city,
			District district, String address, String introduction, String avatar, int japaneseLevel,
			Timestamp createDate, Timestamp updateDate, boolean isDelete) {
		super();
		this.id = id;
		this.user = user;
		this.name = name;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.country = country;
		this.city = city;
		this.district = district;
		this.address = address;
		this.introduction = introduction;
		this.avatar = avatar;
		this.japaneseLevel = japaneseLevel;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.isDelete = isDelete;
	}    
	
	
}
