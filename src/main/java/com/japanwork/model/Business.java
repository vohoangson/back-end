package com.japanwork.model;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "business_type")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private BigInteger id;
    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="uid")
    private UUID uid;
    
    @Column(name="name_vi")
    private String vi;
    
    @Column(name="name_ja")
    private String ja;
    
    @Column(name="description")
    private String desc;
    
    @JsonIgnore
    @Column(name="created_at")
    private Timestamp createAt;
    
    @JsonIgnore
    @Column(name="updated_at")
    private Timestamp updateAt;
    
    @JsonIgnore
    @Column(name="deleted_at")
    private Timestamp deleteAt;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getVi() {
		return vi;
	}

	public void setVi(String vi) {
		this.vi = vi;
	}

	public String getJa() {
		return ja;
	}

	public void setJa(String ja) {
		this.ja = ja;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}	
	
	public UUID getUid() {
		return uid;
	}

	public void setUid(UUID uid) {
		this.uid = uid;
	}

	public Timestamp getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}

	public Timestamp getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}

	public Timestamp isDeleteAt() {
		return deleteAt;
	}

	public void setDeleteAt(Timestamp deleteAt) {
		this.deleteAt = deleteAt;
	}

	public Business(BigInteger id, UUID uid, String vi, String ja, String desc, Timestamp createAt, Timestamp updateAt,
			Timestamp deleteAt) {
		super();
		this.id = id;
		this.uid = uid;
		this.vi = vi;
		this.ja = ja;
		this.desc = desc;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.deleteAt = deleteAt;
	}

	public Business(UUID uid) {
		this.uid = uid;
	}
	
	public Business() {
	}
	
	public static Set<Business> listBusiness(Set<UUID> businessIds){
		Set<Business> businesses = new HashSet<>();
		for (UUID id : businessIds) {
			Business business = new Business(id);
			businesses.add(business);
		}
		return businesses;
	}
	
	public static Set<UUID> listBusinessID(Set<Business> businesses){
		Set<UUID> businessIds = new HashSet<>();
		for (Business obj : businesses) {
			businessIds.add(obj.getUid());
		}
		return businessIds;
	}
}
