package com.treading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treading.entities.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long>
{

	
}
