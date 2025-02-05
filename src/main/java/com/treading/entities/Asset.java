package com.treading.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Asset 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private double quantity;
	
	private double buyPrice;
	
	@ManyToOne
	private Coin coin;
	
	@ManyToOne
	private User user;
}
