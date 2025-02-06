package com.treading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.treading.entities.Coin;
import com.treading.service.CoinService;

@RestController
@RequestMapping("/coins")
public class CoinController 
{
	@Autowired
	private CoinService coinService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@GetMapping
	public ResponseEntity<List<Coin>> getCoinList(@RequestParam(name="page", required = false) int page) throws JsonMappingException, JsonProcessingException, Exception
	{
		List<Coin> coins = coinService.getCoinList(page);
		
		return new ResponseEntity<>(coins, HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("/{coinId}/chart")
	public ResponseEntity<JsonNode> getMarketChart(@PathVariable String coinId , @RequestParam("days") int days) throws JsonMappingException, JsonProcessingException, Exception
	{
		String coins = coinService.getMarketChart(coinId, days);
		
		JsonNode jsonNode = objectMapper.readTree(coins);
		
		return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("/search")
	public ResponseEntity<JsonNode> searchCoin( @RequestParam("q") String keyword) throws JsonMappingException, JsonProcessingException, Exception
	{
		String coins = coinService.searchCoin(keyword);
		
		JsonNode jsonNode = objectMapper.readTree(coins);
		
		return new ResponseEntity<>(jsonNode, HttpStatus.OK);
	}
	
	
	@GetMapping("/top50")
	public ResponseEntity<JsonNode> getTop50CoinbyMarketCapRank( ) throws JsonMappingException, JsonProcessingException, Exception
	{
		String coins = coinService.getTop50CoinsByMarketCapRank();
		
		JsonNode jsonNode = objectMapper.readTree(coins);
		
		return new ResponseEntity<>(jsonNode, HttpStatus.OK);
	}
	
	
	@GetMapping("/trending")
	public ResponseEntity<JsonNode> getTreadingCoin( ) throws JsonMappingException, JsonProcessingException, Exception
	{
		String coins = coinService.getTreadingCoins();
		
		JsonNode jsonNode = objectMapper.readTree(coins);
		
		return  ResponseEntity.ok(jsonNode);
	}
	
	
	@GetMapping("/details/{coinId}")
	public ResponseEntity<JsonNode> getCoinDetails(@PathVariable String coinId ) throws JsonMappingException, JsonProcessingException, Exception
	{
		String coins = coinService.getCoinDetails(coinId);
		
		JsonNode jsonNode = objectMapper.readTree(coins);
		
		return new ResponseEntity<>(jsonNode, HttpStatus.OK);
	}
	

}
