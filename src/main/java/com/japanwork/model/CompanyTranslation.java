package com.japanwork.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="company_translation")
public class CompanyTranslation {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private UUID id;
    
	@ManyToOne
    @JoinColumn(name="company_id")
    @Where(clause = "is_delete = false")
    private Company company;
    
    @ManyToOne
    @JoinColumn(name="translator_id")
    @Where(clause = "is_delete = false")
    private Translator translator;
    
	@Column(name="name")
    private String name;
    
    @Column(name="introduction")
    private String introduction;
    
    @Column(name="address")
    private String address;
    
    @Column(name="status")
    private int status;
    
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Translator getTranslator() {
		return translator;
	}

	public void setTranslator(Translator translator) {
		this.translator = translator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public CompanyTranslation(UUID id, Company company, Translator translator, String name, String introduction,
			String address, int status, Timestamp createDate, Timestamp updateDate, boolean isDelete) {
		super();
		this.id = id;
		this.company = company;
		this.translator = translator;
		this.name = name;
		this.introduction = introduction;
		this.address = address;
		this.status = status;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.isDelete = isDelete;
	}

	public CompanyTranslation() {
		super();
	}
}
