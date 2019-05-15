package com.japanwork.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "business_type")
public class BusinessType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;
    
    @JsonProperty("name_vi")
    @Column(name="name_vi")
    private String nameVi;
    
    @JsonProperty("name_ja")
    @Column(name="name_ja")
    private String nameJa;
    
    @Column(name="description")
    private String description;
    
    @JsonProperty("create_date")
    @Column(name="create_date")
    private Timestamp createDate;
    
    @JsonProperty("update_date")
    @Column(name="update_date")
    private Timestamp updateDate;
    
    @JsonProperty("is_delete")
    @Column(name="is_delete")
    private boolean isDelete;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNameVi() {
		return nameVi;
	}

	public void setNameVi(String nameVi) {
		this.nameVi = nameVi;
	}

	public String getNameJa() {
		return nameJa;
	}

	public void setNameJa(String nameJa) {
		this.nameJa = nameJa;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public BusinessType(UUID id, String nameVi, String nameJa, String description, Timestamp createDate,
			Timestamp updateDate, boolean isDelete) {
		super();
		this.id = id;
		this.nameVi = nameVi;
		this.nameJa = nameJa;
		this.description = description;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.isDelete = isDelete;
	}

	public BusinessType() {
		super();
	}
}
