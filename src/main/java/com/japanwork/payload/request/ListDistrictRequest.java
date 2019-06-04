package com.japanwork.payload.request;

import java.util.Set;

public class ListDistrictRequest {
	private Set<DistrictRequest> districts;

	public Set<DistrictRequest> getDistricts() {
		return districts;
	}

	public void setDistricts(Set<DistrictRequest> districts) {
		this.districts = districts;
	}
}
