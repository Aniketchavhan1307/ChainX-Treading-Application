package com.treading.service;

import com.treading.entities.Order;
import com.treading.entities.User;
import com.treading.entities.Wallet;

public interface WalletService 
{
	Wallet getUserWallet(User user);
	
	Wallet addBalanceToWallet(Wallet wallet, Long money);
	
	Wallet findByWalletId(Long id) throws Exception;
	
	Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception;
	
	Wallet payOrderPayment(Order order, User user) throws Exception;
	
	

}
