package com.treading.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.treading.domain.VerificationType;
import com.treading.entities.ForgotPasswordToken;
import com.treading.entities.User;
import com.treading.entities.VerificationCode;
import com.treading.request.ForgotPasswordTokenRequest;
import com.treading.request.ResetPasswordRequest;
import com.treading.response.ApiResponse;
import com.treading.response.AuthResponse;
import com.treading.service.EmailService;
import com.treading.service.ForgotPasswordService;
import com.treading.service.UserService;
import com.treading.service.VerificationCodeService;
import com.treading.utils.OtpUtils;

@RestController
public class UserController 
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private VerificationCodeService verificationCodeService;
	
	@Autowired
	private ForgotPasswordService forgotPasswordService;
	
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
	
	
	
	
	@PostMapping("/auth/users/reset-password/send-otp")
	public ResponseEntity<AuthResponse> sendForgotPasswordOTP(
												@RequestBody ForgotPasswordTokenRequest request) throws Exception
	{
		User user = userService.findUserByEmail(request.getSendTo());
		
		String otp = OtpUtils.generateOTP();
		
		String id = UUID.randomUUID().toString();
		
		ForgotPasswordToken token =  forgotPasswordService.findByUser(user.getId());
		
		if (token == null)
		{
			token = forgotPasswordService.createToken(user, id, otp, request.getVerificationType(), request.getSendTo() );
			
		}
		
		
		if(request.getVerificationType().equals(VerificationType.EMAIL))
		{
			emailService.sendVerificationOtpEmail(user.getEmail(), token.getOtp());
		}
		
		AuthResponse response = new AuthResponse();
		
		response.setMessage("Password reset otp sent successfully");
		response.setSession(token.getId());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	
	@PatchMapping("/auth/users/reset-password/verify-otp")
	public ResponseEntity<ApiResponse> resetPassword(@RequestHeader("Authorization") String jwt,
												@RequestParam 	String id,
												@RequestBody ResetPasswordRequest request) throws Exception
	{
		
		ForgotPasswordToken token = forgotPasswordService.findById(id);
		
		boolean isVerified = token.getOtp().equals(request.getOtp());
		
		if (isVerified)
		{
			userService.updatePassword(token.getUser(), request.getPassword());
			
			ApiResponse res = new ApiResponse();
			
			res.setMessage("Password update successfully...");
			
			return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
			
		}
		
		 throw new Exception("Wrong Otp");
	}

}
