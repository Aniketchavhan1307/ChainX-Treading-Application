package com.treading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.treading.entities.Order;
import com.treading.entities.PaymentOrder;
import com.treading.entities.User;
import com.treading.entities.Wallet;
import com.treading.entities.WalletTransation;
import com.treading.response.PaymentResponse;
import com.treading.service.OrderService;
import com.treading.service.PaymentService;
import com.treading.service.UserService;
import com.treading.service.WalletService;

@RestController
//@RequestMapping("/api/wallet")
public class WalletController
{
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PaymentService paymentService;
	
	
	@GetMapping("/api/wallet")
	public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception
	{
		User user = userService.findUserByProfileByJwt(jwt);
		
		Wallet wallet = walletService.getUserWallet(user);
		
		return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/api/wallet/{walletId}/transfer")
	public ResponseEntity<Wallet> wallletToWalletTransfer(@RequestHeader("Authorization") String jwt,
												@PathVariable	Long walletId,
												@RequestBody WalletTransation req) throws Exception
	{
		User senderUser = userService.findUserByProfileByJwt(jwt);
		
		Wallet receiverWallet = walletService.findByWalletId(walletId);
		
		Wallet wallet = walletService.walletToWalletTransfer(senderUser, 
															receiverWallet, 
															req.getAmount());
		
		
		return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
	}

	
	@PutMapping("/api/wallet/order/{orderId}/pay")
	public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization") String jwt,
												@PathVariable	Long orderId
												)  throws Exception
	{
		User user = userService.findUserByProfileByJwt(jwt);
		
		Order order = orderService.getOrderById(orderId);
		
		Wallet wallet = walletService.payOrderPayment(order, user);
		
		return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/api/wallet/deposit")
	public ResponseEntity<Wallet> addBalanceToWallet(@RequestHeader("Authorization") String jwt,
												@RequestParam("order_id") Long orderId,
												@RequestParam("payment_id") String paymentId
												)  throws Exception
	{
		User user = userService.findUserByProfileByJwt(jwt);
		
		
		Wallet wallet = walletService.getUserWallet(user);
		
		PaymentOrder order = paymentService.getPaymentOrderById(orderId);
		
		Boolean status = paymentService.proceedPaymentOrder(order, paymentId);
		
//		PaymentResponse response = new PaymentResponse();
//		response.setPayment_url("Deposite success");

		if (status)
		{
			wallet = walletService.addBalanceToWallet(wallet, order.getAmount());
		}
		
		
		return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
	}

}
