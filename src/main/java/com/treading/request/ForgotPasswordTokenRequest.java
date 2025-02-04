package com.treading.request;

import com.treading.domain.VerificationType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForgotPasswordTokenRequest 
{
	private String sendTo;
	
	private VerificationType verificationType;
	
	

}
