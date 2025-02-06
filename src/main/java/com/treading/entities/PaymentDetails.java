package com.treading.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class PaymentDetails 
{

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;
	
	private String accountNumber;
	
	private String accountHolderName;
	
	private String ifsc;
	
	private String bankName;
	
	
	@OneToOne
	@JsonProperty(access = Access.WRITE_ONLY)
	private User user;
	
	
}
