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
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.repository.city.CityRepository;

@Service
public class CityService {
	@Autowired
	private CityRepository cityRepository;
	
	public BaseDataResponse findAllByIsDelete() {
		List<City> list = cityRepository.findAllByIsDelete(false);		
		BaseDataResponse response = new BaseDataResponse(list);	
		return response;
	}
	
	public BaseDataResponse listCityByCountry(UUID id) {
		List<City> list = cityRepository.findAllByCountryIdAndIsDelete(id, false);		
		BaseDataResponse response = new BaseDataResponse(list);	
		return response;
	}
	
	public BaseDataResponse findByIdAndIsDelete(UUID id) {
		City city = cityRepository.findByIdAndIsDelete(id, false);
		if(city == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}
		
		BaseDataResponse response = new BaseDataResponse(city);	
		return response;
	}
	
	public BaseDataResponse save(CityRequest cityRequest) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		City city = new City();
		city.setCountry(cityRequest.getCountry());
		city.setNameJa(cityRequest.getNameJa());
		city.setNameVi(cityRequest.getNameVi());
		city.setDescription(cityRequest.getDescription());
		city.setCreateDate(timestamp);
		city.setUpdateDate(timestamp);
		city.setDelete(false);
		
		City result = cityRepository.save(city);
		BaseDataResponse response = new BaseDataResponse(result);
		
		return response;
	}
	
	public BaseDataResponse saves(ListCityRequest listCityRequest) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		List<City> listCity = new ArrayList<>();
		for (CityRequest cityRequest : listCityRequest.getCities()) {
			City obj = new City();
			
			obj.setCountry(cityRequest.getCountry());
			obj.setNameJa(cityRequest.getNameJa());
			obj.setNameVi(cityRequest.getNameVi());
			obj.setDescription(cityRequest.getDescription());
			obj.setCreateDate(timestamp);
			obj.setUpdateDate(timestamp);
			obj.setDelete(false);
			
			listCity.add(obj);
		}
		List<City> result = cityRepository.saveAll(listCity);
		BaseDataResponse response = new BaseDataResponse(result);
		
		return response;
	}
}
