package com.japanwork.repository.company;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Company;
import com.japanwork.model.User;

public interface CompanyRepository extends JpaRepository<Company, BigInteger>{
	public Company findByUidAndDeletedAt(UUID id, Timestamp isDelete);
	public List<Company> findAllByDeletedAt(Timestamp isDelete);
	public Page<Company> findAllByDeletedAt(Pageable page, Timestamp isDelete);
	public Company findByUserAndDeletedAt(User user, Timestamp isDelete);
	public Company findByUser(User user);
	public Company findByUid(UUID uid);
}
