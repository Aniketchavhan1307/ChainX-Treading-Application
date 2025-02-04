package com.treading.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.treading.domain.USER_ROLE;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String fullName;

	private String email;
	
	
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)    // password will be only writable when we fetch by api it will not display
	private String password;
	
	private USER_ROLE roles = USER_ROLE.ROLE_CUSTOMER;
	
	
	@Embedded
	private TwoFactorAuth twoFactorAuth =  new TwoFactorAuth();
	
	

}
