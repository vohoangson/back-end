package com.japanwork.repository.candidate;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Candidate;
import com.japanwork.model.User;

public interface CandidateRepository extends JpaRepository<Candidate, UUID>{
	public Candidate findByIdAndIsDelete(UUID id, boolean isDelete);
	public List<Candidate> findAllByIsDelete(boolean isDelete);
	public Candidate findByUser(User user);
}
