package com.japanwork.repository.academy;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Academy;

public interface AcademyRepository extends JpaRepository<Academy, UUID>{
	public Academy findByIdAndIsDelete(UUID id, boolean isDelete);
	public List<Academy> findByCandidateId(UUID id);
}
