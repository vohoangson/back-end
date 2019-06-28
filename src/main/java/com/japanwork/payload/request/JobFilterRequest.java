package com.japanwork.payload.request;

import java.util.List;

public class JobFilterRequest {
	private String jobName;
	
	private String companyName;
	
	private List<String> businessIds;
	
	private List<String> contractIds;
	
	private List<String> levelIds;
	
	private List<String> cityIds;

	private int minSalary;
	
	private String postTime;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public List<String> getBusinessIds() {
		return businessIds;
	}

	public void setBusinessIds(List<String> businessIds) {
		this.businessIds = businessIds;
	}

	public List<String> getContractIds() {
		return contractIds;
	}

	public void setContractIds(List<String> contractIds) {
		this.contractIds = contractIds;
	}

	public List<String> getLevelIds() {
		return levelIds;
	}

	public void setLevelIds(List<String> levelIds) {
		this.levelIds = levelIds;
	}

	public List<String> getCityIds() {
		return cityIds;
	}

	public void setCityIds(List<String> cityIds) {
		this.cityIds = cityIds;
	}

	public int getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(int minSalary) {
		this.minSalary = minSalary;
	}

	public String getPostTime() {
		return postTime;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
