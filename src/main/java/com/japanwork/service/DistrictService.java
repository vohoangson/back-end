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
import com.japanwork.repository.district.DistrictRepository;

@Service
public class DistrictService {
	@Autowired
	private DistrictRepository districtRepository;
	
	public District save(DistrictRequest districtRequest) {
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
		return result;
	}
	
	public List<District> findAllByIsDelete() {
		List<District> list = districtRepository.findAllByIsDelete(false);
		return list;
	}
	public List<District> findAllByCityIdAndIsDelete(UUID id){
		List<District> list = districtRepository.findAllByCityIdAndIsDelete(id, false);
		return list;
	}
	public District findByIdAndIsDelete(UUID id) {
		District district = districtRepository.findByIdAndIsDelete(id, false);
		if(district == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		return district;
	}
	
	public List<District> saves(ListDistrictRequest listDistrictRequest) {
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
		return result;
	}
}
