package com.treading.service;

import com.treading.domain.VerificationType;
import com.treading.entities.User;
import com.treading.entities.VerificationCode;

public interface VerificationCodeService 
{
	VerificationCode sendVerificationCode(User user, VerificationType verificationType);
	
	VerificationCode getVerificationCodeById(Long id) throws Exception;
	
	VerificationCode getVerificationCodeByUser(Long userId);
	
	void deleteVerificationCodeById(VerificationCode code);
	
	

}
