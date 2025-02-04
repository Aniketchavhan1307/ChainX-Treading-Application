package com.treading.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetPasswordRequest
{
	private String otp;
	
	private String password;
}
