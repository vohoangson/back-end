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
    private Timestamp createdAt;
    
    @JsonIgnore
    @Column(name="updated_at")
    private Timestamp updatedAt;
    
    @JsonIgnore
    @Column(name="deleted_at")
    private Timestamp deletedAt;

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

	

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

	public Business(BigInteger id, UUID uid, String vi, String ja, String desc, Timestamp createdAt,
			Timestamp updatedAt, Timestamp deletedAt) {
		super();
		this.id = id;
		this.uid = uid;
		this.vi = vi;
		this.ja = ja;
		this.desc = desc;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
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
