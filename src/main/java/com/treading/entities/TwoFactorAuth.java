package com.treading.entities;

import com.treading.domain.VerificationType;

import lombok.Data;

@Data
public class TwoFactorAuth 
{
	private boolean isEnable = false;
	
	private VerificationType sendTo;
}
