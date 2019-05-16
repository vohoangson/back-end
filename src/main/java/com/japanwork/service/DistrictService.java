package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.model.District;
import com.japanwork.payload.request.DistrictRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.repository.district.DistrictRepository;

@Service
public class DistrictService {
	@Autowired
	private DistrictRepository districtRepository;
	
	public BaseDataResponse save(DistrictRequest districtRequest) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		District district = new District();
		district.setNameJa(districtRequest.getNameJa());
		district.setNameVi(districtRequest.getNameVi());
		district.setCountryCode(districtRequest.getCountryCode());
		district.setDescription(districtRequest.getDescription());
		district.setCreateDate(timestamp);
		district.setUpdateDate(timestamp);
		district.setIsDelete(false);
		
		District result = districtRepository.save(district);
		BaseDataResponse response = new BaseDataResponse(result);
		return response;
	}
	
	public District findById(UUID id) {
		return districtRepository.findByIdAndIsDelete(id, false);
	}
}
