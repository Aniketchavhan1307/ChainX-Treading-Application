package com.treading.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treading.entities.PaymentDetails;
import com.treading.entities.User;
import com.treading.repository.PaymentDetailsRepository;

@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService
{

	@Autowired
	private PaymentDetailsRepository paymentDetailsRepository;
	

	public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc,
			String bankName, User user) 
	
	{
		PaymentDetails paymentDetails = new PaymentDetails();
		
		paymentDetails.setAccountNumber(accountNumber);
		paymentDetails.setAccountHolderName(accountHolderName);
		paymentDetails.setIfsc(ifsc);
		paymentDetails.setBankName(bankName);
		paymentDetails.setUser(user);
		

		return paymentDetailsRepository.save(paymentDetails);
	}

	public PaymentDetails getUserPaymentDetails(User user)
	{
		return paymentDetailsRepository.findByUserId(user.getId());
	}

}
