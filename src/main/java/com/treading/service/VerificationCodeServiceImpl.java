package com.treading.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treading.domain.VerificationType;
import com.treading.entities.User;
import com.treading.entities.VerificationCode;
import com.treading.repository.VerificationCodeRepository;
import com.treading.utils.OtpUtils;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService
{
	@Autowired
	private VerificationCodeRepository verificationCodeRepository;

	public VerificationCode sendVerificationCode(User user, VerificationType verificationType) 
	{
		VerificationCode verifyCode = new VerificationCode();
		
		verifyCode.setOtp(OtpUtils.generateOTP());
		verifyCode.setVerificationType(verificationType);
		verifyCode.setUser(user);
		
		return verificationCodeRepository.save(verifyCode);
	}
	
	

	public VerificationCode getVerificationCodeById(Long id) throws Exception 
	{
		Optional<VerificationCode> verificationCode = verificationCodeRepository.findById(id);
		
		if (verificationCode.isPresent())
		{
			return verificationCode.get();
		}
		
		throw new Exception("Verification code not found");
	}
	
	

	public VerificationCode getVerificationCodeByUser(Long userId) 
	{
		return verificationCodeRepository.findByUserId(userId);
	}
	
	

	public void deleteVerificationCodeById(VerificationCode code) 
	{
		verificationCodeRepository.delete(code);
	}
	
}
