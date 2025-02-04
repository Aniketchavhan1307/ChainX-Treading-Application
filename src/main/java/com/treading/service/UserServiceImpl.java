package com.treading.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.treading.config.JwtProvider;
import com.treading.domain.VerificationType;
import com.treading.entities.TwoFactorAuth;
import com.treading.entities.User;
import com.treading.repository.UserRepository;

@Service
//@RestController
public class UserServiceImpl implements UserService
{
	
	@Autowired
	private UserRepository userRepository;

	
	public User findUserByProfileByJwt(String jwt) throws Exception 
	{
		String email = JwtProvider.getEmailFromJwtToken(jwt);
		
		User user = userRepository.findByEmail(email);
		
		if (user == null)
		{
			throw new Exception("User not found");
		}
	
		return user;
	}

	
	public User findUserByEmail(String email) throws Exception
	{
		User user = userRepository.findByEmail(email);
		
		if (user == null)
		{
			throw new Exception("User not found");
		}
	
		return user;
	}
	
	

	public User findUserById(Long userId) throws Exception 
	{
		Optional<User> user = userRepository.findById(userId);
		
		if (user.isEmpty())
		{
			throw new Exception("User not found");
		}
		
		return user.get();
	}
	

	

	public User updatePassword(User user, String newPassword)
	{
		user.setPassword(newPassword);
		
		return userRepository.save(user);
	}


	public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user) 
	{
		TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
		twoFactorAuth.setEnable(true);
		twoFactorAuth.setSendTo(verificationType);
		
		user.setTwoFactorAuth(twoFactorAuth);
		
		return userRepository.save(user);
	}

}
