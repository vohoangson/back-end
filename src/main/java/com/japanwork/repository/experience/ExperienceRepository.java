package com.japanwork.repository.experience;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, UUID>{
	public Experience findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
	public List<Experience> findByCandidateId(UUID id);
}
