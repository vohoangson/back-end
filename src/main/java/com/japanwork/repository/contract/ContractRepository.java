package com.japanwork.repository.contract;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Contract;

public interface ContractRepository extends JpaRepository<Contract, UUID>{
	public Contract findByIdAndIsDelete(UUID id, boolean isDelete);
}
