package com.treading.utils;

import java.util.Random;

public class OtpUtils 
{
	public static String generateOTP()
	{
		int otpLength = 6;
		
		Random random = new Random();
		
		StringBuilder otp = new StringBuilder(otpLength);
	
		for (int i = 0; i < otp.length(); i++) 
		{
			otp.append(random.nextInt(10));
		}
		
		return otp.toString();
	}

}
