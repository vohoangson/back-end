package com.japanwork.repository.experience;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, BigInteger>{
	public Experience findByUidAndDeletedAt(UUID id, Timestamp isDelete);
	public List<Experience> findByCandidateId(UUID id);
}
