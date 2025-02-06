package com.treading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treading.entities.Coin;
import com.treading.entities.User;
import com.treading.entities.WatchList;
import com.treading.service.CoinService;
import com.treading.service.UserService;
import com.treading.service.WatchListService;

@RestController
@RequestMapping("/api/watchList")
public class WatchListController
{
	@Autowired
	private WatchListService watchListService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CoinService coinService;
	
	
	@GetMapping("/user")
	public ResponseEntity<WatchList> getUserWatchList(@RequestHeader("Authorization") String jwt) throws Exception
	{
		User user = userService.findUserByProfileByJwt(jwt);
		
		WatchList  watchList = watchListService.findUserWatchList(user.getId());
		
		return ResponseEntity.ok(watchList);
	}
	
	
//	@PostMapping("/create")
//	public ResponseEntity<WatchList> createWatchList(@RequestHeader("Authorization") String jwt) throws Exception
//	{
//		User user = userService.findUserByProfileByJwt(jwt);
//		
//		WatchList  createdWatchList = watchListService.createWatchList(user);
//		
//		return ResponseEntity.status(HttpStatus.CREATED).body(createdWatchList);
//	}
	
	
	
	@GetMapping("/{watchListId}")
	public ResponseEntity<WatchList> getWatchListById(@PathVariable Long watchListId) throws Exception
	{
		
		WatchList  watchList = watchListService.findById(watchListId);
		
		return ResponseEntity.ok(watchList);
	}
	
	
	
	@PatchMapping("/add/coin/{coinId}")
	public ResponseEntity<Coin> addItemToWatchList(@RequestHeader("Authorization") String jwt,
															@PathVariable String coinId) throws Exception
	{
		User user = userService.findUserByProfileByJwt(jwt);
		
		Coin coin = coinService.findById(coinId);
		
		Coin addedCoin = watchListService.addItemToWatchList(coin, user);
		
		return ResponseEntity.ok(addedCoin);
	}
	
}
