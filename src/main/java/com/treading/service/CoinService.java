package com.treading.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.treading.entities.Coin;

public interface CoinService
{
	List<Coin> getCoinList(int page) throws JsonMappingException, JsonProcessingException, Exception;
	
	String getMarketChart(String coinId, int days) throws JsonMappingException, JsonProcessingException, Exception;
	
	String getCoinDetails(String coinId) throws JsonMappingException, JsonProcessingException, Exception; 			// access from coinGecko Website
	
	Coin findById(String coinId) throws Exception;					// access from our database
	
	String searchCoin(String keyword) throws Exception;
	
	String getTop50CoinsByMarketCapRank() throws Exception;
	
	String getTreadingCoins() throws Exception;

}
