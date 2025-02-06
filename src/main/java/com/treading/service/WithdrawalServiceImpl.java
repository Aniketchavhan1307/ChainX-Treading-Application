package com.treading.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treading.domain.WithdrawalStatus;
import com.treading.entities.User;
import com.treading.entities.Withdrawal;
import com.treading.repository.WithdrawalRepository;

@Service
public class WithdrawalServiceImpl implements WithdrawalService
{
	@Autowired
	private WithdrawalRepository withdrawalRepository;

	public Withdrawal requestWithdrawal(Long amount, User user)
	{
		Withdrawal withdrawal = new Withdrawal();
		withdrawal.setAmount(amount);
		withdrawal.setUser(user);
		withdrawal.setStatus(WithdrawalStatus.PENDING);
	
		return withdrawalRepository.save(withdrawal);
	}

	
	public Withdrawal procedWithdrawal(Long withdrawalId, boolean accept) throws Exception
	{
		Optional<Withdrawal> withdrawal = withdrawalRepository.findById(withdrawalId);
		
		if (withdrawal.isEmpty()) 
		{
			throw new Exception("Withdrawal not found");
		}
		
		Withdrawal withdrawal2 = withdrawal.get();
		
		withdrawal2.setDate(LocalDateTime.now());
		
		if (accept)
		{
			withdrawal2.setStatus(WithdrawalStatus.SUCCESS);
		}
		else
		{
			withdrawal2.setStatus(WithdrawalStatus.PENDING);
		}
		
		return withdrawalRepository.save(withdrawal2);
	}

	
	
	public List<Withdrawal> getUsersWithdrawalHistory(User user) 
	{
		return withdrawalRepository.findByUserId(user.getId());
	}

	public List<Withdrawal> getAllWithdrawalRequest()
	{
		return withdrawalRepository.findAll();
	}

}
