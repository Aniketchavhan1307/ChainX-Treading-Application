package com.treading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.treading.domain.VerificationType;
import com.treading.entities.User;
import com.treading.entities.VerificationCode;
import com.treading.service.EmailService;
import com.treading.service.UserService;
import com.treading.service.VerificationCodeService;

@RestController
public class UserController 
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private VerificationCodeService verificationCodeService;
	
	private String jwt;
	
	
	@GetMapping("/api/users/profile")
	public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception
	{
		User user = userService.findUserByProfileByJwt(jwt);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
	@PostMapping("/api/users/varification/{verificationType}/send-otp")
	public ResponseEntity<String> sendVerificationOTP(@RequestHeader("Authorization") String jwt,
												@PathVariable VerificationType verificationType) throws Exception
	{
		
		User user = userService.findUserByProfileByJwt(jwt);
		
		VerificationCode verificationCode = verificationCodeService.
											getVerificationCodeByUser(user.getId());
		
		if(verificationCode == null)
		{
			verificationCode = verificationCodeService
								.sendVerificationCode(user, verificationType);
		
		}
		
		if(verificationType.equals(verificationType.EMAIL))
		{
			emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
		}
		
		
		return new ResponseEntity<>("Verification otp send successfully...", HttpStatus.OK);
	}
	
	
	
	
	@PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
	public ResponseEntity<User> enableTwoFactorAuthentication(@RequestHeader("Authorization") String jwt,
														@PathVariable String otp) throws Exception
	{
		User user = userService.findUserByProfileByJwt(jwt);
		
		VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());
		
		String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL) 
						? verificationCode.getEmail() : verificationCode.getMobile();
		
		 boolean isVerified = verificationCode.getOtp().equals(otp);
		 
		 if (isVerified)
		 {
			 User updatedUser = userService
					 .enableTwoFactorAuthentication(verificationCode.getVerificationType(),
							 sendTo, user);
			 
			 verificationCodeService.deleteVerificationCodeById(verificationCode);
			 
			 return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
			
		}
		
		 throw new Exception("Wrong Otp");
	}
	
	

}
