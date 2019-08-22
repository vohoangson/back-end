package com.japanwork.repository.company;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Company;
import com.japanwork.model.User;

public interface CompanyRepository extends JpaRepository<Company, UUID>{
	public Company findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
	public List<Company> findAllByDeletedAt(Timestamp deletedAt);
	public Page<Company> findAllByDeletedAt(Pageable page, Timestamp deletedAt);
	public Company findByUserIdAndDeletedAt(UUID id, Timestamp deletedAt);
	public Company findByUser(User user);
	public Page<Company> findAllByIdInAndDeletedAt(Pageable page, Iterable<UUID> ids, Timestamp deletedAt);

//	public CompanyTranslation findByCompanyAndDeletedAt(Company company, Timestamp deletedAt);
}
