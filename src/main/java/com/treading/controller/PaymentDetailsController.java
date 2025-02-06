package com.treading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treading.entities.PaymentDetails;
import com.treading.entities.User;
import com.treading.service.PaymentDetailsService;
import com.treading.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping
public class PaymentDetailsController 
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private PaymentDetailsService paymentDetailsService;
	
	
	@PostMapping("/payment-details")
	public ResponseEntity<PaymentDetails> addPaymentDetails(
								@RequestBody PaymentDetails paymentDetailsRequest,
								@RequestHeader("Authorization") String jwt) throws Exception
	{
		User user = userService.findUserByProfileByJwt(jwt);
		
		PaymentDetails paymentDetails = paymentDetailsService.addPaymentDetails(
												paymentDetailsRequest.getAccountNumber(),
												paymentDetailsRequest.getAccountHolderName(), 
												paymentDetailsRequest.getIfsc(), 
												paymentDetailsRequest.getBankName(),
												user);
		
		
		return new ResponseEntity<PaymentDetails>(paymentDetails, HttpStatus.CREATED);
				
	}
	
	
	@GetMapping("/payment-details")
	public ResponseEntity<PaymentDetails> getUserPaymentDetails(@RequestHeader("Authorization") String jwt) throws Exception 
	{
		User user = userService.findUserByProfileByJwt(jwt);
		
		PaymentDetails paymentDetails = paymentDetailsService.getUserPaymentDetails(user);
		
		return new ResponseEntity<PaymentDetails>(paymentDetails, HttpStatus.OK);
	}
	
	

}
