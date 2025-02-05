package com.treading.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treading.domain.OrderStatus;
import com.treading.domain.OrderType;
import com.treading.entities.Asset;
import com.treading.entities.Coin;
import com.treading.entities.Order;
import com.treading.entities.OrderItem;
import com.treading.entities.User;
import com.treading.repository.OrderItemRepository;
import com.treading.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl  implements OrderService
{
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private WalletService walletService;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private AssetService assetService;
	
	
	public Order createOrder(User user, OrderItem orderItem, OrderType orderType)
	{
		double price = orderItem.getCoin().getCurrentPrice() * orderItem.getQuantity();
		
		Order order = new Order();
		order.setUser(user);
		order.setOrderItem(orderItem);
		order.setOrderType(orderType);
		order.setPrice(BigDecimal.valueOf(price));
		order.setTimestamp(LocalDateTime.now());
		order.setStatus(OrderStatus.PENDING);
		
		 return orderRepository.save(order);
		
	}

	public Order getOrderById(Long orderId) throws Exception 
	{
		return orderRepository.findById(orderId)
				.orElseThrow(()-> new Exception("Order not found"));
	}

	public List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol)
	{
		return orderRepository.findByUserId(userId);
	}
	
	
	
	// this method need in processOrder that why we declared here..........
	
	private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice)
	{
		OrderItem item = new OrderItem();
		item.setCoin(coin);
		item.setQuantity(quantity);
		item.setBuyPrice(buyPrice);
		item.setSellPrice(sellPrice);
		
		return orderItemRepository.save(item);
	}
	
	
	
	
	@Transactional
	public Order buyAsset(Coin coin, double quantity, User user) throws Exception
	{
		if (quantity<=0)
		{
			throw new Exception("Quantity should be > 0");
		}
		
		double buyPrice = coin.getCurrentPrice();
		
		OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, 0);
		
		Order order = createOrder(user, orderItem, OrderType.BUY);
		
		orderItem.setOrder(order);
		
		walletService.payOrderPayment(order, user);
		
		order.setStatus(OrderStatus.SUCCESS);
		order.setOrderType(OrderType.BUY);
		
		Order savedOrder= orderRepository.save(order);
		
		// create asset.....
		
		Asset oldAsset = assetService.findAssetByUserIdAndCoinId(
												order.getUser().getId(), 
												order.getOrderItem().getCoin().getId());
		
		if(oldAsset == null)
		{
			assetService.createAsset(user, orderItem.getCoin(), orderItem.getQuantity());
		}
		else
		{
			assetService.updateAsset(oldAsset.getId(), quantity);
		}
		
		
		return savedOrder;
		
	}
	
	@Transactional
	public Order sellAsset(Coin coin, double quantity, User user) throws Exception
	{
		if (quantity<=0)
		{
			throw new Exception("Quantity should be > 0");
		}
		
		double sellPrice = coin.getCurrentPrice();
		
		Asset assetToSell = assetService.findAssetByUserIdAndCoinId(user.getId(), coin.getId());
		
		double buyPrice = assetToSell.getBuyPrice();
		
		if(assetToSell != null)
		{
			OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);
		
		
		
		Order order = createOrder(user, orderItem, OrderType.SELL);
		
		orderItem.setOrder(order);
		
		
		
		if (assetToSell.getQuantity() >= quantity)
		{
			walletService.payOrderPayment(order, user);
			
			Asset updatedAsset = assetService.updateAsset(assetToSell.getId(), -quantity);
			
			order.setStatus(OrderStatus.SUCCESS);
			order.setOrderType(OrderType.SELL);
			
			Order savedOrder= orderRepository.save(order);
			
			
			if(updatedAsset.getQuantity() * coin.getCurrentPrice() <= 1)
			{
				assetService.deleteAsset(updatedAsset.getId());
			}
			
			return savedOrder;
		
		}

		throw new Exception("Insufficient quantity to sell...");
		}
		
		throw new Exception("Asset not found");
	}
	
	
	
	
	
	@Transactional
	public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception
	{
		if (orderType.equals(OrderType.BUY))
		{
			return buyAsset(coin, quantity, user);
		}
		else if(orderType.equals(OrderType.SELL) )
		{
			return sellAsset(coin, quantity, user);
		}
		else
		{
			throw new Exception("Invalid order Type");
		}
		
	}

}
