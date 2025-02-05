package com.treading.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treading.entities.Asset;

public interface AssetRepository extends JpaRepository<Asset, Long>
{
	List<Asset> findByUserId(Long userId);
	
	Asset findByUserIdAndCoinId(Long userId, String coinId);
}
