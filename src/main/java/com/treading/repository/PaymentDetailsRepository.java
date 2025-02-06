package com.treading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treading.entities.PaymentDetails;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long>
{

	PaymentDetails findByUserId(Long userId);
}
