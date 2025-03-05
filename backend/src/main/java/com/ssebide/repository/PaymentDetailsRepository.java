package com.ssebide.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssebide.modal.PaymentDetails;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {

    PaymentDetails findByUserId(Long userId);
}
