package com.treading.service;

import com.treading.entities.TwoFactorOTP;
import com.treading.entities.User;

public interface TwoFactorOtpService 
{
	TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt);
	
	TwoFactorOTP FindByUser(Long userId);
	
	TwoFactorOTP FindById(String id);
	
	boolean verifyTwoFactorOtp(TwoFactorOTP factorOTP, String otp);
	
	void deleteTwoFactorOtp(TwoFactorOTP factorOTP);
	

}
