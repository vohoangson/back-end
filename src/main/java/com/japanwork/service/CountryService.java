package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Country;
import com.japanwork.payload.request.CountryRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.repository.country.CountryRepository;

@Service
public class CountryService {
	@Autowired
	private CountryRepository countryRepository;
	
	public BaseDataResponse findAllByIsDelete() {
		List<Country> list = countryRepository.findAllByIsDelete(false);		
		BaseDataResponse response = new BaseDataResponse(list);	
		return response;
	}
	
	public BaseDataResponse findByIdAndIsDelete(UUID id) {
		Country country = countryRepository.findByIdAndIsDelete(id, false);
		if(country == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		BaseDataResponse response = new BaseDataResponse(country);	
		return response;
	}
	
	public BaseDataResponse save(CountryRequest countryRequest) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Country country = new Country();
		country.setJa(countryRequest.getJa());
		country.setVi(countryRequest.getVi());
		country.setDesc(countryRequest.getDesc());
		country.setCreateDate(timestamp);
		country.setUpdateDate(timestamp);
		country.setDelete(false);;
		
		Country result = countryRepository.save(country);
		BaseDataResponse response = new BaseDataResponse(result);
		
		return response;
	}
}
