package com.japanwork.repository.company;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID>{

}
