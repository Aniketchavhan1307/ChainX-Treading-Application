package com.treading.entities;

import com.treading.domain.PaymentMethod;
import com.treading.domain.PaymentOrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class PaymentOrder 
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long amount;
	
	private PaymentOrderStatus status;
	
	private PaymentMethod paymentMethod;
	
	@ManyToOne
	private User user;
	
}
