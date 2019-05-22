package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.City;
import com.japanwork.payload.request.CityRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.repository.city.CityRepository;

@Service
public class CityService {
	@Autowired
	private CityRepository cityRepository;
	
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
		city.setNameJa(cityRequest.getNameJa());
		city.setNameVi(cityRequest.getNameVi());
		city.setDescription(cityRequest.getDescription());
		city.setCreateDate(timestamp);
		city.setUpdateDate(timestamp);
		city.setDelete(false);;
		
		City result = cityRepository.save(city);
		BaseDataResponse response = new BaseDataResponse(result);
		
		return response;
	}
}
