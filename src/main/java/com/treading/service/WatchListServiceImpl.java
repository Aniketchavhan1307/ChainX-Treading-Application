package com.treading.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treading.entities.Coin;
import com.treading.entities.User;
import com.treading.entities.WatchList;
import com.treading.repository.WatchListRepository;

@Service
public class WatchListServiceImpl implements WatchListService
{
	@Autowired
	private WatchListRepository watchListRepository;

	
	
	public WatchList findUserWatchList(Long userId) throws Exception 
	{
		WatchList watchList = watchListRepository.findByUserId(userId);
		
		if(watchList == null)
		{
			throw new Exception("WatchList not found");
		}
		
		return watchList;
	}
	
	

	public WatchList createWatchList(User user) 
	{
		WatchList watchList = new WatchList();
		watchList.setUser(user);
		
		return watchListRepository.save(watchList);
	}

	
	public WatchList findById(Long id) throws Exception 
	{
		Optional<WatchList> watchListOptional = watchListRepository.findById(id);
		
		if (watchListOptional.isEmpty())
		{
			throw new Exception("WatchList not found");
		}
		
		return watchListOptional.get();
	}
	
	

	public Coin addItemToWatchList(Coin coin, User user) throws Exception 
	{
		WatchList watchList = findUserWatchList(user.getId());
		
		if (watchList.getCoins().contains(coin))
		{
			watchList.getCoins().remove(coin);
			
		}
		else
		{
			watchList.getCoins().add(coin);
		}
		
		watchListRepository.save(watchList);
		
		return coin;
	}

}
