package com.treading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treading.entities.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long>
{

	VerificationCode findByUserId(Long userId);
	
}
