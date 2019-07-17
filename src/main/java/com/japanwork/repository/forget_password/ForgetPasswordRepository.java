package com.japanwork.repository.forget_password;

import java.math.BigInteger;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.ForgetPassword;

public interface ForgetPasswordRepository extends JpaRepository<ForgetPassword, BigInteger>{
	public ForgetPassword findByUserUid(UUID id);
	public ForgetPassword findByUserUidAndCode(UUID id, String code);
}
