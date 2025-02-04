package com.treading.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider 
{
	private static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRETE_KEY.getBytes());
	
	
	public static String generateToken(Authentication auth)
	{
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		String roles = populateAutorities(authorities);
		
		String jwt = Jwts.builder().setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+ 86400000))
				.claim("email", auth.getName())
				.claim("authorities", roles)
				.signWith(key)
				.compact()
				;
		
		return jwt;
	}
	
	public static String getEmailFromJwtToken(String token)
	{
		token = token.substring(7);
		
		Claims claims = (Claims) Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
		
		String email = String.valueOf(claims.get("email"));
		
		return email;
	}
	


	private static String populateAutorities(Collection<? extends GrantedAuthority> authorities) 
	{
		Set<String> auth = new HashSet<>();
		
		for(GrantedAuthority grant : authorities)
		{
			auth.add(grant.getAuthority());
		}
		
		return String.join(",", auth);
	}
	
	
}


