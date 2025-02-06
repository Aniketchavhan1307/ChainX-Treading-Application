package com.treading.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		String jwt = request.getHeader(JwtConstant.JWT_HEADER);
		
		if(jwt != null)
		{
			jwt = jwt.substring(7);
			
			try 
			{
				SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRETE_KEY.getBytes());
				// Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
				 //
				System.err.println("claim done");
				Claims claims = (Claims) Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
				System.err.println("start done");
				String email = String.valueOf(claims.get("email"));
				System.err.println("email done");
				String authorities = String.valueOf(claims.get("authorities"));
				System.err.println("athorities done");
				List<GrantedAuthority> authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				System.err.println("mid done");
				Authentication auth = new UsernamePasswordAuthenticationToken(
						email, 
						null, 
						authoritiesList
						);
				System.err.println("All done");
			
				SecurityContextHolder.getContext().setAuthentication(auth);
					
			} 
			catch (Exception e) 
			{
				throw new RuntimeException("Invalid token");
			}
		}
		
		filterChain.doFilter(request, response);
	}

}




//===================================================================

//package com.treading.config;
//
//import java.io.IOException;
//import java.util.List;
//import javax.crypto.SecretKey;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.SignatureException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//public class JwtTokenValidator extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String jwt = request.getHeader(JwtConstant.JWT_HEADER);
//
//        if (jwt != null) {
//            try {
//                // Ensure token has "Bearer " prefix
//                if (!jwt.startsWith("Bearer ")) {
//                    throw new RuntimeException("Invalid token format");
//                }
//                jwt = jwt.substring(7);
//
//                // Secret key
//                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRETE_KEY.getBytes());
//
//                // Parse token
//                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
//
//                // Debugging: Log claims
//                System.out.println("Claims: " + claims);
//
//                // Extract details
//                String email = claims.get("email", String.class);
//                String authorities = claims.get("authorities", String.class);
//
//                if (email == null || authorities == null) {
//                    throw new RuntimeException("Token missing required claims");
//                }
//
//                List<GrantedAuthority> authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
//
//                // Set authentication context
//                Authentication auth = new UsernamePasswordAuthenticationToken(email, null, authoritiesList);
//                SecurityContextHolder.getContext().setAuthentication(auth);
//
//            } catch (ExpiredJwtException e) {
//                throw new RuntimeException("Token expired");
//            } catch (SignatureException e) {
//                throw new RuntimeException("Invalid token signature");
//            } catch (Exception e) {
//                throw new RuntimeException("Invalid token: " + e.getMessage());
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
