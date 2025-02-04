package com.treading.service;

import com.treading.domain.VerificationType;
import com.treading.entities.ForgotPasswordToken;
import com.treading.entities.User;

public interface ForgotPasswordService
{
	ForgotPasswordToken createToken(User user, String id, String otp, 
									VerificationType verificationType , String sendTo);
	
	ForgotPasswordToken findById(String id);
	
	ForgotPasswordToken findByUser(Long userId);
	
	void deleteToken(ForgotPasswordToken token);
	
	
	
	
}
