package com.treading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treading.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>
{

}
