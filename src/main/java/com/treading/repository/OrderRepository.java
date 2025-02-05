package com.treading.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treading.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>
{
	List<Order> findByUserId(Long userId);
}
