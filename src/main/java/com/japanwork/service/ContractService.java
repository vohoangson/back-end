package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Contract;
import com.japanwork.payload.request.ContractRequest;
import com.japanwork.repository.contract.ContractRepository;

@Service
public class ContractService {
	@Autowired
	private ContractRepository contractRepository;
	
	public List<Contract> findAllByIsDelete() {
		List<Contract> list = contractRepository.findAllByDeletedAt(null);
		return list;
	}
	
	public Contract findByIdAndIsDelete(UUID id) {
		Contract contract = contractRepository.findByUidAndDeletedAt(id, null);
		if(contract == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		return contract;
	}
	
	public Contract save(ContractRequest contractRequest) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Contract contract = new Contract();
		contract.setJa(contractRequest.getJa());
		contract.setVi(contractRequest.getVi());
		contract.setDesc(contractRequest.getDesc());
		contract.setCreatedAt(timestamp);
		contract.setUpdatedAt(timestamp);
		contract.setDeletedAt(null);
		
		Contract result = contractRepository.save(contract);
		
		return result;
	}
}
