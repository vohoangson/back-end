package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Contract;
import com.japanwork.payload.request.ContractRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.ContractResponse;
import com.japanwork.repository.contract.ContractRepository;

@Service
public class ContractService {
	@Autowired
	private ContractRepository contractRepository;
	
	public Contract findByIdAndIsDelete(UUID id) {
		return contractRepository.findByIdAndIsDelete(id,false);
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
		contract.setIsDelete(false);
		
		Contract result = contractRepository.save(contract);
		ContractResponse contractResponse = this.setContractResponse(result);
		BaseDataResponse response = new BaseDataResponse(contractResponse);
		
		return response;
	}
	
	public ContractResponse convertContract(UUID id) throws ResourceNotFoundException{
		Contract contract = contractRepository.findByIdAndIsDelete(id, false);
		if(contract == null) {
			throw new ResourceNotFoundException("Contract not found for this id :: " + id);
		}
		ContractResponse contractResponse = this.setContractResponse(contract);
		
		return contractResponse;
	}
	
	private ContractResponse setContractResponse(Contract contract) {
		ContractResponse contractResponse = new ContractResponse();
		contractResponse.setNameVi(contract.getNameVi());
		contractResponse.setNameJa(contract.getNameJa());
		contractResponse.setDescription(contract.getDescription());
		
		return contractResponse;
	}
}
