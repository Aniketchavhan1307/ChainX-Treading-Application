package com.treading.service;

import java.util.List;

import com.treading.entities.User;
import com.treading.entities.Withdrawal;

public interface WithdrawalService 
{
	Withdrawal requestWithdrawal(Long amount, User user);
	
	Withdrawal procedWithdrawal(Long withdrawalId, boolean accept) throws Exception;
	
	List<Withdrawal> getUsersWithdrawalHistory(User user);
	
	List<Withdrawal> getAllWithdrawalRequest();
	
	

}
