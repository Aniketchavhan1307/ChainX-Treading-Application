package com.treading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treading.entities.Coin;

public interface CoinRepository extends JpaRepository<Coin, String>
{

}
