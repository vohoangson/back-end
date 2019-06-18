package com.japanwork.repository.forget_password;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.ForgetPassword;

public interface ForgetPasswordRepository extends JpaRepository<ForgetPassword, UUID>{
	public ForgetPassword findByUserId(UUID id);
	public ForgetPassword findByUserIdAndCode(UUID id, String code);
}
