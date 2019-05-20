package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.District;
import com.japanwork.payload.request.DistrictRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.DistrictResponse;
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
		DistrictResponse districtResponse = this.setDistrictResponse(result);
		BaseDataResponse response = new BaseDataResponse(districtResponse);
		return response;
	}
	
	public District findByIdAndIsDelete(UUID id) {
		return districtRepository.findByIdAndIsDelete(id, false);
	}
	
	public DistrictResponse convertDistrict(UUID id) throws ResourceNotFoundException{
		District district = districtRepository.findByIdAndIsDelete(id, false);
		if(district == null) {
			throw new ResourceNotFoundException("District not found for this id :: " + id);
		}
		DistrictResponse districtResponse = this.setDistrictResponse(district);
		
		return districtResponse;
	}
	
	private DistrictResponse setDistrictResponse(District district) {
		DistrictResponse districtResponse = new DistrictResponse();
		districtResponse.setNameVi(district.getNameVi());
		districtResponse.setNameJa(district.getNameJa());
		districtResponse.setDescription(district.getDescription());
		return districtResponse;
	}
}
