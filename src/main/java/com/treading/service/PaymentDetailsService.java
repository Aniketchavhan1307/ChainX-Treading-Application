package com.treading.service;

import com.treading.entities.PaymentDetails;
import com.treading.entities.User;

public interface PaymentDetailsService 
{

	PaymentDetails addPaymentDetails(String accountNumber,
										String accountHolderName,
										String ifsc,
										String bankName,
										User user);
	
	
	public PaymentDetails getUserPaymentDetails(User user);
										
}
