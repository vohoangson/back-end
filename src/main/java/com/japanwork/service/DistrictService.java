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
import com.japanwork.model.District;
import com.japanwork.payload.request.DistrictRequest;
import com.japanwork.payload.request.ListDistrictRequest;
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
		district.setCity(district.getCity());
		district.setJa(districtRequest.getJa());
		district.setVi(districtRequest.getVi());
		district.setDesc(districtRequest.getDesc());
		district.setCreateDate(timestamp);
		district.setUpdateDate(timestamp);
		district.setDelete(false);
		
		District result = districtRepository.save(district);
		BaseDataResponse response = new BaseDataResponse(result);
		return response;
	}
	
	public BaseDataResponse findAllByIsDelete() {
		List<District> list = districtRepository.findAllByIsDelete(false);
		BaseDataResponse response = new BaseDataResponse(list);	
		return response;
	}
	public BaseDataResponse findAllByCityIdAndIsDelete(UUID id){
		List<District> list = districtRepository.findAllByCityIdAndIsDelete(id, false);
		BaseDataResponse response = new BaseDataResponse(list);	
		return response;
	}
	public BaseDataResponse findByIdAndIsDelete(UUID id) {
		District district = districtRepository.findByIdAndIsDelete(id, false);
		if(district == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}
		
		BaseDataResponse response = new BaseDataResponse(district);	
		return response;
	}
	
	public BaseDataResponse saves(ListDistrictRequest listDistrictRequest) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		List<District> listDistrict = new ArrayList<>();
		for (DistrictRequest districtRequest : listDistrictRequest.getDistricts()){
			District obj = new District();
			
			obj.setCity(districtRequest.getCity());
			obj.setJa(districtRequest.getJa());
			obj.setVi(districtRequest.getVi());
			obj.setDesc(districtRequest.getDesc());
			obj.setCreateDate(timestamp);
			obj.setUpdateDate(timestamp);
			obj.setDelete(false);
			
			listDistrict.add(obj);
		}
		List<District> result = districtRepository.saveAll(listDistrict);
		BaseDataResponse response = new BaseDataResponse(result);
		
		return response;
	}
}
