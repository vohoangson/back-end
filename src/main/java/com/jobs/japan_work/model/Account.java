package com.jobs.japan_work.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	@Column(name="email")
	private String email;
	@Column(name="username")
	private String userName;
	@Column(name="password")
	private String password;
	@Column(name="provider")
	private String provider;
	@Column(name="role")
	private String role;
	@Column(name="create_date")
	private Timestamp create_date;
	@Column(name="update_date")
	private Timestamp update_date;
	@Column(name="enabled")
	private int enabled;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Timestamp getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}
	public Timestamp getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public Account() {
		super();
	}
	public Account(UUID id, String email, String userName, String password, String provider, String role,
			Timestamp create_date, Timestamp update_date, int enabled) {
		super();
		this.id = id;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.provider = provider;
		this.role = role;
		this.create_date = create_date;
		this.update_date = update_date;
		this.enabled = enabled;
	}
	
	
}
