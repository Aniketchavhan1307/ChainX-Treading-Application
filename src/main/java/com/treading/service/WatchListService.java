package com.treading.service;

import com.treading.entities.Coin;
import com.treading.entities.User;
import com.treading.entities.WatchList;

public interface WatchListService
{
	WatchList findUserWatchList(Long userId) throws Exception;
	
	WatchList createWatchList(User user);
	
	WatchList findById(Long id) throws Exception;
	
	Coin addItemToWatchList(Coin coin, User user) throws Exception;

}
