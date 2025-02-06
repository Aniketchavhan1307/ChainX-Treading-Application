package com.treading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treading.domain.WalletTransactionType;
import com.treading.entities.User;
import com.treading.entities.Wallet;
import com.treading.entities.WalletTransation;
import com.treading.entities.Withdrawal;
import com.treading.service.UserService;
import com.treading.service.WalletService;
import com.treading.service.WithdrawalService;

@RestController
//@RequestMapping("/api/withdrawal")
public class WithdrawalController
{

	@Autowired
	private WithdrawalService withdrawalService;
	
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private UserService userService;
	
//	@Autowired
//	private WalletTransactionService walletTransactionService;
	
	
	@PostMapping("/api/withdrawal/{amount}")
	public ResponseEntity<?> withdrawalRequest(@PathVariable Long amount, 
												@RequestHeader("Authorization") String jwt) throws Exception
	{
		User user = userService.findUserByProfileByJwt(jwt);
		
		Wallet userWallet = walletService.getUserWallet(user);
		
		Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
		
		walletService.addBalanceToWallet(userWallet, -withdrawal.getAmount());
		
//		WalletTransation walletTransation = walletTransactionService.createTransaction(
//												userWallet,
//												WalletTransactionType.WITHDRAWAL, 
//												null,
//												"Bank account withdrawal",
//												withdrawal.getAmount());
//		
		
		
		return new ResponseEntity<>(withdrawal, HttpStatus.OK);
	}
	
	
	
	@PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
	public ResponseEntity<?> proceedWithdrawal(@PathVariable Long id, 
												@PathVariable boolean accept,
												@RequestHeader("Authorization") String jwt) throws Exception
	{
		User user = userService.findUserByProfileByJwt(jwt);
		
		Wallet userWallet = walletService.getUserWallet(user);
		
		Withdrawal withdrawal = withdrawalService.procedWithdrawal(id, accept);
		
		if(!accept)
		{
			walletService.addBalanceToWallet(userWallet, withdrawal.getAmount());
		}
		
		
		
		return new ResponseEntity<>(withdrawal, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/api/withdrawal")
	public ResponseEntity<?> getWithdrawalHistory(@RequestHeader("Authorization") String jwt) throws Exception
	{
		User user = userService.findUserByProfileByJwt(jwt);
		
		
		List<Withdrawal> withdrawal = withdrawalService.getUsersWithdrawalHistory(user);
		
		return new ResponseEntity<>(withdrawal, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/api/admin/withdrawal")
	public ResponseEntity<?> getAllWithdrawalRequest(@RequestHeader("Authorization") String jwt) throws Exception
	{
		User user = userService.findUserByProfileByJwt(jwt);
		
		
		List<Withdrawal> withdrawal = withdrawalService.getAllWithdrawalRequest();
		
		return new ResponseEntity<>(withdrawal, HttpStatus.OK);
	}
	
	
	
	
	
}
