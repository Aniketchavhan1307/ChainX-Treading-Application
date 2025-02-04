package com.treading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treading.config.JwtProvider;
import com.treading.entities.User;
import com.treading.repository.UserRepository;
import com.treading.response.AuthResponse;

@RestController
@RequestMapping("/auth")
public class AuthController 
{
	
		@Autowired
		private UserRepository userRepository;
		
		@PostMapping("/signup")
		public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception
		{
			
			User isEmailExist = userRepository.findByEmail(user.getEmail());
			
			if (isEmailExist != null) 
			{
				throw new Exception("Email is already Exist with another account..!");
			}
			
			
			
			User newUser = new User();
			
			newUser.setFullName(user.getFullName());
			newUser.setEmail(user.getEmail());
			newUser.setPassword(user.getPassword());
			
			
			User savedUser = userRepository.save(newUser);
			
			Authentication auth = new UsernamePasswordAuthenticationToken(
					newUser.getEmail(), 
					newUser.getPassword()
					);
		
			SecurityContextHolder.getContext().setAuthentication(auth);
				
			String jwt = JwtProvider.generateToken(auth);
			
			AuthResponse res = new AuthResponse();
			res.setJwt(jwt);
			res.setStatus(true);
			res.setMessage("Register success");
			
			return new ResponseEntity<>(res, HttpStatus.CREATED);
		}

}
