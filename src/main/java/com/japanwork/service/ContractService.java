package com.japanwork.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.common.CommonFunction;
import com.japanwork.model.Contract;
import com.japanwork.payload.request.ContractRequest;
import com.japanwork.repository.contract.ContractRepository;

@Service
public class ContractService {
	@Autowired
	private ContractRepository contractRepository;

	public List<Contract> index() {
		List<Contract> list = contractRepository.findAllByDeletedAt(null);
		return list;
	}

	public Contract create(ContractRequest contractRequest) {
		Contract contract = new Contract();
		contract.setJa(contractRequest.getJa());
		contract.setVi(contractRequest.getVi());
		contract.setDesc(contractRequest.getDesc());
		contract.setCreatedAt(CommonFunction.getCurrentDateTime());
		contract.setUpdatedAt(CommonFunction.getCurrentDateTime());

		Contract result = contractRepository.save(contract);

		return result;
	}
}
