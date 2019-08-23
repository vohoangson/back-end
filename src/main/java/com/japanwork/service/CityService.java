package com.japanwork.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.common.CommonFunction;
import com.japanwork.model.City;
import com.japanwork.payload.request.CityRequest;
import com.japanwork.payload.request.ListCityRequest;
import com.japanwork.repository.city.CityRepository;

@Service
public class CityService {
	@Autowired
	private CityRepository cityRepository;

	public List<City> index() {
		List<City> list = cityRepository.findAllByDeletedAt(null);
		return list;
	}

	public City create(CityRequest cityRequest) {
		City city = new City();
		city.setCountryCode(cityRequest.getCountryCode());
		city.setJa(cityRequest.getJa());
		city.setVi(cityRequest.getVi());
		city.setDesc(cityRequest.getDesc());
		city.setCreatedAt(CommonFunction.getCurrentDateTime());
		city.setUpdatedAt(CommonFunction.getCurrentDateTime());

		City result = cityRepository.save(city);

		return result;
	}

	public List<City> listCityByCountry(String code) {
		List<City> list = cityRepository.findAllByCountryCodeAndDeletedAt(code, null);
		return list;
	}

	public List<City> saves(ListCityRequest listCityRequest) {
		List<City> listCity = new ArrayList<>();
		for (CityRequest cityRequest : listCityRequest.getCities()) {
			City obj = new City();

			obj.setCountryCode(cityRequest.getCountryCode());
			obj.setJa(cityRequest.getJa());
			obj.setVi(cityRequest.getVi());
			obj.setDesc(cityRequest.getDesc());
			obj.setCreatedAt(CommonFunction.getCurrentDateTime());
			obj.setUpdatedAt(CommonFunction.getCurrentDateTime());

			listCity.add(obj);
		}
		List<City> result = cityRepository.saveAll(listCity);

		return result;
	}
}
