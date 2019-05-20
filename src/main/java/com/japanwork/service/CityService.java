package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.City;
import com.japanwork.payload.request.CityRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.CityResponse;
import com.japanwork.repository.city.CityRepository;

@Service
public class CityService {
	@Autowired
	private CityRepository cityRepository;
	
	public City findByIdAndIsDelete(UUID id) {
		return cityRepository.findByIdAndIsDelete(id,false);
	}
	
	public BaseDataResponse save(CityRequest cityRequest) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		City city = new City();
		city.setNameJa(cityRequest.getNameJa());
		city.setNameVi(cityRequest.getNameVi());
		city.setDescription(cityRequest.getDescription());
		city.setCreateDate(timestamp);
		city.setUpdateDate(timestamp);
		city.setDelete(false);;
		
		City result = cityRepository.save(city);
		CityResponse cityResponse = this.setCityResponse(result);
		BaseDataResponse response = new BaseDataResponse(cityResponse);
		
		return response;
	}
	
	public CityResponse convertCity(UUID id) throws ResourceNotFoundException{
		City city = cityRepository.findByIdAndIsDelete(id, false);
		if(city == null) {
			throw new ResourceNotFoundException("City not found for this id :: " + id);
		}
		CityResponse cityResponse = this.setCityResponse(city);
		
		return cityResponse;
	}
	
	private CityResponse setCityResponse(City city) {
		CityResponse cityResponse = new CityResponse();
		cityResponse.setNameVi(city.getNameVi());
		cityResponse.setNameJa(city.getNameJa());
		cityResponse.setDescription(city.getDescription());
		
		return cityResponse;
	}
}
