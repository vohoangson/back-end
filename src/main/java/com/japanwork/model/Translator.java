package com.japanwork.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="translator")
public class Translator {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;
	
	@Column(name="account_id")
    private UUID accountId;
    
    @Column(name="name")
    private String name;
    
    @Column(name="gender")
    private int gender;
    
    @Column(name="dob")
    private Date dob;
    
    @Column(name="living_district_id")
    private UUID livingDistrictId;
    
    @Column(name="living_address")
    private String livingAddress;
    
    @Column(name="introduction")
    private String introduction;
    
    @Column(name="avatar_url")
    private String avatarUrl;
    
    @Column(name="japanese_level")
    private int japaneseLevel;
    
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

	public UUID getAccountId() {
		return accountId;
	}

	public void setAccountId(UUID accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public UUID getLivingDistrictId() {
		return livingDistrictId;
	}

	public void setLivingDistrictId(UUID livingDistrictId) {
		this.livingDistrictId = livingDistrictId;
	}

	public String getLivingAddress() {
		return livingAddress;
	}

	public void setLivingAddress(String livingAddress) {
		this.livingAddress = livingAddress;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public int getJapaneseLevel() {
		return japaneseLevel;
	}
	void setJapaneseLevel(int japaneseLevel) {
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

	public boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Translator(UUID id, UUID account_id, String name, int gender, Date dob, UUID livingDistrictId,
			String livingAddress, String introduction, String avatarUrl, int japaneseLevel, 
			Timestamp createDate, Timestamp updateDate, boolean isDelete) {
		super();
		this.id = id;
		this.accountId = account_id;
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.livingDistrictId = livingDistrictId;
		this.livingAddress = livingAddress;
		this.introduction = introduction;
		this.avatarUrl = avatarUrl;
		this.japaneseLevel = japaneseLevel;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.isDelete = isDelete;
	}

	public Translator() {
		super();
	}    
}
