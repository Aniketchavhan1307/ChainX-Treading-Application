package com.treading.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.treading.domain.VerificationType;
import com.treading.entities.ForgotPasswordToken;
import com.treading.entities.User;
import com.treading.repository.ForgotPasswordRepository;

@Service
public class ForgotPasswordImpl implements ForgotPasswordService
{
	
	private ForgotPasswordRepository passwordRepository;

	
	
	
	public ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType,
			String sendTo) 
	{
		ForgotPasswordToken token = new ForgotPasswordToken();
		token.setUser(user);
		token.setSendTo(sendTo);
		token.setVerificationType(verificationType);
		token.setOtp(otp);
		token.setId(id);
		
	return	passwordRepository.save(token);
		 
	}

	
	
	public ForgotPasswordToken findById(String id) 
	{
		Optional<ForgotPasswordToken> token = passwordRepository.findById(id);
		
		return token.orElse(null);
	}

	
	
	public ForgotPasswordToken findByUser(Long userId) 
	{
		
		return passwordRepository.findByUserId(userId);
	}

	
	
	public void deleteToken(ForgotPasswordToken token)
	{
		passwordRepository.delete(token);
	}
	
}
