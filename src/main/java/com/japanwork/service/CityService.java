package com.japanwork.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.City;
import com.japanwork.payload.request.CityRequest;
import com.japanwork.payload.request.ListCityRequest;
import com.japanwork.repository.city.CityRepository;

@Service
public class CityService {
	@Autowired
	private CityRepository cityRepository;
	
	public List<City> findAllByIsDelete() {
		List<City> list = cityRepository.findAllByIsDelete(false);
		return list;
	}
	
	public List<City> listCityByCountry(String code) {
		List<City> list = cityRepository.findAllByCountryCodeAndIsDelete(code, false);
		return list;
	}
	
	public City findByIdAndIsDelete(UUID id) {
		City city = cityRepository.findByIdAndIsDelete(id, false);
		if(city == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		return city;
	}
	
	public City save(CityRequest cityRequest) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		City city = new City();
		city.setCountryCode(cityRequest.getCountryCode());
		city.setJa(cityRequest.getJa());
		city.setVi(cityRequest.getVi());
		city.setDesc(cityRequest.getDesc());
		city.setCreateDate(timestamp);
		city.setUpdateDate(timestamp);
		city.setDelete(false);
		
		City result = cityRepository.save(city);
		
		return result;
	}
	
	public List<City> saves(ListCityRequest listCityRequest) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		List<City> listCity = new ArrayList<>();
		for (CityRequest cityRequest : listCityRequest.getCities()) {
			City obj = new City();
			
			obj.setCountryCode(cityRequest.getCountryCode());
			obj.setJa(cityRequest.getJa());
			obj.setVi(cityRequest.getVi());
			obj.setDesc(cityRequest.getDesc());
			obj.setCreateDate(timestamp);
			obj.setUpdateDate(timestamp);
			obj.setDelete(false);
			
			listCity.add(obj);
		}
		List<City> result = cityRepository.saveAll(listCity);
		
		return result;
	}
}
