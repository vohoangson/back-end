package com.japanwork.repository.company;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID>{
	public Company findByIdAndIsDelete(UUID id, boolean isDelete);
	public List<Company> findAllByIsDelete(boolean isDelete);
}
