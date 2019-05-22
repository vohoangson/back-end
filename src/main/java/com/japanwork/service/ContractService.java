package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Contract;
import com.japanwork.payload.request.ContractRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.repository.contract.ContractRepository;

@Service
public class ContractService {
	@Autowired
	private ContractRepository contractRepository;
	
	public BaseDataResponse findByIdAndIsDelete(UUID id) {
		Contract contract = contractRepository.findByIdAndIsDelete(id, false);
		if(contract == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404);
		}
		
		BaseDataResponse response = new BaseDataResponse(contract);	
		return response;
	}
	
	public BaseDataResponse save(ContractRequest contractRequest) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Contract contract = new Contract();
		contract.setNameJa(contractRequest.getNameJa());
		contract.setNameVi(contractRequest.getNameVi());
		contract.setDescription(contractRequest.getDescription());
		contract.setCreateDate(timestamp);
		contract.setUpdateDate(timestamp);
		contract.setDelete(false);
		
		Contract result = contractRepository.save(contract);
		BaseDataResponse response = new BaseDataResponse(result);
		
		return response;
	}
}
