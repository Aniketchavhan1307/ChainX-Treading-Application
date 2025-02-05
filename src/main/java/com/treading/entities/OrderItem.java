package com.treading.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class OrderItem 
{
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;
	
	private double quantity;
	
	@ManyToOne
	private Coin coin;
	
	private double buyPrice;
	
	private double sellPrice;
	
	@JsonIgnore
	@OneToOne
	private Order order;

}
