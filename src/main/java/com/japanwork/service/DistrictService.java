package com.japanwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.model.District;
import com.japanwork.repository.district.DistrictRepository;

@Service
public class DistrictService {
	@Autowired
	private DistrictRepository districtRepository;
	
	public District save(District district) {
		return districtRepository.save(district);
	}
}
