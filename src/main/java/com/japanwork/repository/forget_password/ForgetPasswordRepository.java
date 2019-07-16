package com.japanwork.repository.forget_password;

import java.math.BigInteger;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.ForgetPassword;

public interface ForgetPasswordRepository extends JpaRepository<ForgetPassword, BigInteger>{
	public ForgetPassword findByUserId(UUID uid);
	public ForgetPassword findByUserIdAndCode(UUID uid, String code);
}
