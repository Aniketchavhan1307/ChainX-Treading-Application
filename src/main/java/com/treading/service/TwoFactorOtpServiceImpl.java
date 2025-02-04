package com.treading.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treading.entities.TwoFactorOTP;
import com.treading.entities.User;
import com.treading.repository.TwoFactorOtpRepository;

@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService
{

	@Autowired
	private TwoFactorOtpRepository twoFactorOtpRepository;
	
	
	public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt)
	{
		String id = UUID.randomUUID().toString();
		
		TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
		twoFactorOTP.setId(id);
		twoFactorOTP.setOtp(otp);
		twoFactorOTP.setJwt(jwt);
		twoFactorOTP.setUser(user);
		
		return twoFactorOtpRepository.save(twoFactorOTP);
	}

	
	public TwoFactorOTP FindByUser(Long userId) 
	{
		return twoFactorOtpRepository.findByUserId(userId);
	}
	

	public TwoFactorOTP FindById(String id) 
	{
		Optional<TwoFactorOTP> opt = twoFactorOtpRepository.findById(id);
		
		return opt.orElseThrow(null);
	}

	public boolean verifyTwoFactorOtp(TwoFactorOTP factorOTP, String otp) 
	{
		
		return factorOTP.getOtp().equals(otp);
	}
	

	public void deleteTwoFactorOtp(TwoFactorOTP factorOTP) 
	{
		twoFactorOtpRepository.delete(factorOTP);
	}

}
