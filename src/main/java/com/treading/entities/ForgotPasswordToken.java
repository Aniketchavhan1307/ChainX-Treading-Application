package com.treading.entities;

import com.treading.domain.VerificationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ForgotPasswordToken 
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@OneToOne
	private User user;
	
	private String otp;
	
	private VerificationType verificationType;
	
	private String sendTo;
}
