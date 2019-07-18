package com.japanwork.repository.contract;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Contract;

public interface ContractRepository extends JpaRepository<Contract, UUID>{
	public Contract findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
	public List<Contract> findAllByDeletedAt(Timestamp deletedAt);
}
