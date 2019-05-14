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
@Table(name="company_translation")
public class CompanyTranslation {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;
    
    @Column(name="company_id")
    private UUID companyId;
    
    @Column(name="translator_id")
    private UUID translator_id;
    
	@Column(name="name")
    private String name;
    
    @Column(name="description")
    private String description;
    
    @Column(name="status")
    private int status;
    
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

	public UUID getCompanyId() {
		return companyId;
	}

	public void setCompanyId(UUID companyId) {
		this.companyId = companyId;
	}

	public UUID getTranslator_id() {
		return translator_id;
	}

	public void setTranslator_id(UUID translator_id) {
		this.translator_id = translator_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
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

	public boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public CompanyTranslation(UUID id, UUID companyId, UUID translator_id, String name, String description, int status,
			Timestamp createDate, Timestamp updateDate, boolean isDelete) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.translator_id = translator_id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.isDelete = isDelete;
	}

	public CompanyTranslation() {
		super();
	}
}
