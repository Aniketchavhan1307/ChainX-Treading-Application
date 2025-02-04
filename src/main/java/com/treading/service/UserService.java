package com.treading.service;

import com.treading.domain.VerificationType;
import com.treading.entities.User;

public interface UserService 
{
	User findUserByProfileByJwt(String jwt) throws Exception;
	
	User findUserByEmail(String email) throws Exception;
	
	User findUserById(Long userId) throws Exception;
	
	User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user);
	
	User updatePassword(User user, String newPassword);
}

