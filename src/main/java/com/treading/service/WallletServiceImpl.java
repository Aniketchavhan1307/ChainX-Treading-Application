package com.treading.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treading.domain.OrderType;
import com.treading.entities.Order;
import com.treading.entities.User;
import com.treading.entities.Wallet;
import com.treading.repository.WalletRepository;

@Service
public class WallletServiceImpl implements WalletService
{
	
	@Autowired
	private WalletRepository walletRepository;
	
	

	public Wallet getUserWallet(User user) 
	{
		Wallet wallet = walletRepository.findByUserId(user.getId());
		
		if (wallet == null)
		{
			wallet = new Wallet();
			wallet.setUser(user);
		}
		return wallet;
	}
	
	

	public Wallet addBalanceToWallet(Wallet wallet, Long money) 
	{
		BigDecimal balance = wallet.getBalance();
		
		BigDecimal newBalance = balance.add(BigDecimal.valueOf(money));
		
		wallet.setBalance(newBalance);
		
		return walletRepository.save(wallet);
	}

	
	public Wallet findByWalletId(Long id) throws Exception 
	{
		Optional<Wallet> wallet = walletRepository.findById(id);
		
		if (wallet.isPresent())
		{
			return wallet.get();
		}
		
		throw new Exception("Wallet not found");
	}
	
	

	public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception
	{
		Wallet senderWallet = getUserWallet(sender);
		
		if (senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0)
		{
			throw new Exception("Insufficient balance...");
		} 
		
		BigDecimal senderBalance = senderWallet
										.getBalance()
										.subtract(BigDecimal.valueOf(amount));
		
		senderWallet.setBalance(senderBalance);
		
		walletRepository.save(senderWallet);
		
		BigDecimal receiverBalance = receiverWallet
										.getBalance()
										.add(BigDecimal.valueOf(amount));

		
		receiverWallet.setBalance(receiverBalance);
		walletRepository.save(receiverWallet);
		
	
		return senderWallet;
	}

	public Wallet payOrderPayment(Order order, User user) throws Exception
	{
		Wallet wallet = getUserWallet(user);
		
		if(order.getOrderType().equals(OrderType.BUY))
		{
			BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());
			
			if(newBalance.compareTo(order.getPrice()) < 0)
			{
				throw new Exception("Insufficient funds for this transaction");
			}
			
			wallet.setBalance(newBalance);
			
		}
		else
		{
			BigDecimal newBalance = wallet.getBalance().add(order.getPrice());
			
			wallet.setBalance(newBalance);
		}
		
		walletRepository.save(wallet);
		
		return wallet;
	}

}
