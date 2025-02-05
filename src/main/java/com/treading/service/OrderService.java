package com.treading.service;

import java.util.List;

import com.treading.domain.OrderType;
import com.treading.entities.Coin;
import com.treading.entities.Order;
import com.treading.entities.OrderItem;
import com.treading.entities.User;

public interface OrderService 
{
	Order createOrder(User user, OrderItem orderItem, OrderType orderType);
	
	Order getOrderById(Long orderId) throws Exception;
	
	List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol);

	Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;
	
	
	
}
