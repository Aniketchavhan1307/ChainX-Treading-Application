package com.treading.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.treading.domain.PaymentMethod;
import com.treading.domain.PaymentOrderStatus;
import com.treading.entities.PaymentOrder;
import com.treading.entities.User;
import com.treading.repository.PaymentOrderRepository;
import com.treading.response.PaymentResponse;

@Service
public class PaymentServiceImpl implements PaymentService
{
	@Autowired
	private PaymentOrderRepository paymentOrderRepository;
	
	@Value("${stripe.api.key}")
	private String stripeSecretKey;
	
	@Value("${razorpay.api.key}")
	private String razorApiKey;
	
	@Value("${razorpay.api.secret}")
	private String apiSecretKey;
	

	public PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod) 
	{
		PaymentOrder paymentOrder = new PaymentOrder();
		
		paymentOrder.setUser(user);
		paymentOrder.setAmount(amount);
		paymentOrder.setPaymentMethod(paymentMethod);
		
		return paymentOrderRepository.save(paymentOrder);
	}

	public PaymentOrder getPaymentOrderById(Long id) throws Exception 
	{
		return paymentOrderRepository.findById(id)
				.orElseThrow(() -> new Exception("Payment order not found"));
	}

	
	
	public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException 
	{
		if (paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING))
		{
			if (paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY))
			{
				RazorpayClient razorpay = new  RazorpayClient(razorApiKey, apiSecretKey);
			
				Payment payment = razorpay.payments.fetch(paymentId);
				
				Integer amount = payment.get("amount");
				String status = payment.get("status");
				
				if (status.equals("captured"))
				{
					paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
					return true;
				}
				paymentOrder.setStatus(PaymentOrderStatus.FAILED);
				paymentOrderRepository.save(paymentOrder);
				return false;
			
			}
			
			paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
			paymentOrderRepository.save(paymentOrder);
			return true;
			
		}
		
		return false;
	}

	
	
	public PaymentResponse createRazorpayPaymentLink(User user, Long amount) throws RazorpayException
	{
		Long Amount = amount*100;
		 
		 try 
		 {
			 // Instantiate a razorpay client with your key ID and Secret
			 RazorpayClient razorpay = new RazorpayClient(razorApiKey, apiSecretKey);
			 
			 // create JSON object with the payment link request paramenters
			 JSONObject paymentLinkRequest = new JSONObject();
			 
			 paymentLinkRequest.put("amount", amount);
			 paymentLinkRequest.put("currency", "INR");
			 
			 
			 // create a JSON object with the customer details
			 JSONObject customer = new JSONObject();
			 
			 customer.put("name", user.getFullName());
			 customer.put("email", user.getEmail());
			 
			 paymentLinkRequest.put("customer", customer);
			 
			 
			 // create a JSON object with the notification setting 
			 JSONObject notify = new JSONObject();
			 
			 notify.put("email", true);
			 
			 paymentLinkRequest.put("notify", notify);
			 
			 //set the remainder setting 
			 paymentLinkRequest.put("reminder_enable", true);
			 
			 //set the callback URL and method
			 paymentLinkRequest.put("callback_url", "http://localhost:8080/wallet");
			 paymentLinkRequest.put("callback_method", "get");
			 
			 PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
			 
			 String paymentLinkId = payment.get("id");
			 String paymentLinkUrl = payment.get("short_url");
					 
			 PaymentResponse res = new PaymentResponse();
			 res.setPayment_url(paymentLinkUrl);
			 
			 return res;
		 }
		catch (RazorpayException e) 
		 {
			System.out.println("Error creating payment link: "+ e.getMessage());
			throw new RazorpayException(e.getMessage());
		 }
	}
	
	
	

	public PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId)
	{
		return null;
	}

}
