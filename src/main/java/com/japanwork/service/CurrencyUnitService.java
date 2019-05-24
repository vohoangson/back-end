package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.CurrencyUnit;
import com.japanwork.payload.request.CurrencyUnitRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.repository.currency_unit.CurrencyUnitRepository;

@Service
public class CurrencyUnitService {
	@Autowired
	private CurrencyUnitRepository currencyUnitRepository;
	
	public BaseDataResponse save(CurrencyUnitRequest currencyUnitRequest) {
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		CurrencyUnit currencyUnit = new CurrencyUnit();
		currencyUnit.setNameJa(currencyUnitRequest.getNameJa());
		currencyUnit.setNameVi(currencyUnitRequest.getNameVi());
		currencyUnit.setDescription(currencyUnitRequest.getDescription());
		currencyUnit.setCreateDate(timestamp);
		currencyUnit.setUpdateDate(timestamp);
		currencyUnit.setDelete(false);
		
		CurrencyUnit result = currencyUnitRepository.save(currencyUnit);
		BaseDataResponse response = new BaseDataResponse(result);	
		return response;
	}
	
	public BaseDataResponse findByIdAndIsDelete(UUID id) {
		CurrencyUnit currencyUnit = currencyUnitRepository.findByIdAndIsDelete(id, false);
		if(currencyUnit == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}
		
		BaseDataResponse response = new BaseDataResponse(currencyUnit);	
		return response;
	}
}
