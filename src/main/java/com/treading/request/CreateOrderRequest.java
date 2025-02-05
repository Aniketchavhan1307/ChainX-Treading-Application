package com.treading.request;

import com.treading.domain.OrderType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateOrderRequest
{

	private String coinId;
	
	private double quantity;
	
	private OrderType orderType;
}
