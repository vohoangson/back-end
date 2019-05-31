package com.japanwork.repository.experience;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, UUID>{
	public Experience findByIdAndIsDelete(UUID id, boolean isDelete);
	public List<Experience> findByCandidateId(UUID id);
}
