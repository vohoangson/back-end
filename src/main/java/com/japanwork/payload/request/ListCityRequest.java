package com.japanwork.payload.request;

import java.util.Set;

public class ListCityRequest {
	private Set<CityRequest> cities;

	public Set<CityRequest> getCities() {
		return cities;
	}

	public void setCities(Set<CityRequest> cities) {
		this.cities = cities;
	}
}
