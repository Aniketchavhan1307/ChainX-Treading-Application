package com.treading.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treading.entities.Asset;
import com.treading.entities.Coin;
import com.treading.entities.User;
import com.treading.repository.AssetRepository;

@Service
public class AssetServiceImpl implements AssetService
{
	@Autowired
	private AssetRepository assetRepository;
	
	

	public Asset createAsset(User user,Coin coin ,double quantity) 
	{
		Asset asset = new Asset();
		asset.setUser(user);
		asset.setCoin(coin);
		asset.setQuantity(quantity);
		asset.setBuyPrice(coin.getCurrentPrice());
		
		return assetRepository.save(asset);
	}

	public Asset getAssetById(Long assetId) throws Exception 
	{
		return assetRepository.findById(assetId)
					.orElseThrow(()-> new Exception("Asset not found"));
	}

	
	public Asset getAssetByUserId(Long userId, Long assetId) 
	{
		return null;
	}

	public List<Asset> getUserAssets(Long userId) 
	{
		return assetRepository.findByUserId(userId);
	}

	
	
	public Asset updateAsset(Long assetId, double quantity) throws Exception 
	{
		Asset oldAsset = getAssetById(assetId);
		oldAsset.setQuantity(quantity + oldAsset.getQuantity());
		
		return assetRepository.save(oldAsset);
		
	}

	public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) 
	{
		return assetRepository.findByUserIdAndCoinId(userId, coinId);
	}

	public void deleteAsset(Long assetId) 
	{
		assetRepository.deleteById(assetId);
	}

}
