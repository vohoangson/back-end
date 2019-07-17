package com.japanwork.repository.token;


import java.math.BigInteger;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.japanwork.model.VerificationToken;
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, BigInteger>{
	VerificationToken findByToken(String token);
	VerificationToken findByUserUid(UUID id);
}
