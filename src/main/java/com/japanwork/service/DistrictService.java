package com.japanwork.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.common.CommonFunction;
import com.japanwork.model.District;
import com.japanwork.payload.request.DistrictRequest;
import com.japanwork.payload.request.ListDistrictRequest;
import com.japanwork.repository.district.DistrictRepository;

@Service
public class DistrictService {
	@Autowired
	private DistrictRepository districtRepository;

	public List<District> index() {
		List<District> list = districtRepository.findAllByDeletedAt(null);
		return list;
	}

	public District create(DistrictRequest districtRequest) {
		District district = new District();
		district.setCity(district.getCity());
		district.setJa(districtRequest.getJa());
		district.setVi(districtRequest.getVi());
		district.setDesc(districtRequest.getDesc());
		district.setCreatedAt(CommonFunction.getCurrentDateTime());
		district.setUpdatedAt(CommonFunction.getCurrentDateTime());

		District result = districtRepository.save(district);
		return result;
	}

	public List<District> findAllByCityIdAndIsDelete(UUID id){
		List<District> list = districtRepository.findAllByCityIdAndDeletedAt(id, null);
		return list;
	}

	public List<District> saves(ListDistrictRequest listDistrictRequest) {
		List<District> listDistrict = new ArrayList<>();
		for (DistrictRequest districtRequest : listDistrictRequest.getDistricts()){
			District obj = new District();

			obj.setCity(districtRequest.getCity());
			obj.setJa(districtRequest.getJa());
			obj.setVi(districtRequest.getVi());
			obj.setDesc(districtRequest.getDesc());
			obj.setCreatedAt(CommonFunction.getCurrentDateTime());
			obj.setUpdatedAt(CommonFunction.getCurrentDateTime());

			listDistrict.add(obj);
		}
		List<District> result = districtRepository.saveAll(listDistrict);
		return result;
	}
}
