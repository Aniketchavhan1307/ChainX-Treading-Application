package com.treading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treading.entities.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long>
{

	Wallet findByUserId(Long userId);
}
