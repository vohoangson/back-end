package com.japanwork.repository.token;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.japanwork.model.VerificationToken;
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID>{
	VerificationToken findByToken(String token);
}
