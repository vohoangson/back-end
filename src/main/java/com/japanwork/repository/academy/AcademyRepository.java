package com.japanwork.repository.academy;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Academy;

public interface AcademyRepository extends JpaRepository<Academy, UUID>{
	public Academy findByIdAndDeletedAt(UUID id, Timestamp deleteAt);
	public List<Academy> findByCandidateId(UUID id);
}
