package com.treading.entities;

import com.treading.domain.VerificationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class VerificationCode 
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String otp;
	
	@OneToOne
	private User user;
	
	private String email;
	
	private String mobile;
	
	private VerificationType verificationType;
}
