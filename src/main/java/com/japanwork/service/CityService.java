package com.japanwork.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.model.City;
import com.japanwork.repository.city.CityRepository;

@Service
public class CityService {
	@Autowired
	private CityRepository cityRepository;
	
	public City findById(UUID id) {
		return cityRepository.findById(id).get();
	}
}
