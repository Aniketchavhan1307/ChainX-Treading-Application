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
import org.springframework.web.bind.annotation.RestController;

import com.treading.entities.User;
import com.treading.entities.Wallet;
import com.treading.entities.WalletTransation;
import com.treading.service.UserService;
import com.treading.service.WalletService;

@RestController
@RequestMapping("/api/wallet")
public class WalletController
{
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
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

}
