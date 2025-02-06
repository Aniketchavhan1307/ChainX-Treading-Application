package com.treading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treading.domain.PaymentMethod;
import com.treading.entities.PaymentOrder;
import com.treading.entities.User;
import com.treading.response.PaymentResponse;
import com.treading.service.PaymentService;
import com.treading.service.UserService;

@RequestMapping("/api")
@RestController
public class PaymentController 
{

	@Autowired
	private UserService userService;
	
	@Autowired
	private PaymentService paymentService;
	
	
	@PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
	public ResponseEntity<PaymentResponse> paymentHandler(
									@PathVariable PaymentMethod paymentMethod,
									@PathVariable Long amount,
									@RequestHeader("Authorization") String jwt
								) throws Exception
	{
		
		User user= userService.findUserByProfileByJwt(jwt);
		
		PaymentResponse paymentResponse;
		
		PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);
		
		if (paymentMethod.equals(PaymentMethod.RAZORPAY))
		{
			paymentResponse = paymentService.createRazorpayPaymentLink(user, amount);
		}
		else
		{
			paymentResponse = paymentService.createStripePaymentLink(user, amount,order.getId());

		}
		
		return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
		
	}
	
}