package com.treading.entities;

import java.time.LocalDate;

import com.treading.domain.WalletTransactionType;

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
public class WalletTransation 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Wallet wallet;
	
	
	private WalletTransactionType type;
	
	private LocalDate date;
	
	private String transferId;				// where you want to transfer that id

	private String purpose;
	
	private Long amount;
	
	
}
