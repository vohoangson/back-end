package com.japanwork.repository.candidate;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Candidate;
import com.japanwork.model.User;

public interface CandidateRepository extends JpaRepository<Candidate, BigInteger>{
	public Candidate findByUidAndDeletedAt(UUID id, Timestamp deletedAt);
	public List<Candidate> findAllByDeletedAt(Timestamp deletedAt);
	public Candidate findByUser(User user);
	public Page<Candidate> findAllByDeletedAt(Pageable page, Timestamp deletedAt);
	public Candidate findByUserAndDeletedAt(User user, Timestamp deletedAt);
	public Candidate findByUid(UUID id);
}
