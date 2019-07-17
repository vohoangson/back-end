package com.japanwork.repository.contract;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Contract;

public interface ContractRepository extends JpaRepository<Contract, BigInteger>{
	public Contract findByUidAndDeletedAt(UUID id, Timestamp isDelete);
	public List<Contract> findAllByDeletedAt(Timestamp isDelete);
}
