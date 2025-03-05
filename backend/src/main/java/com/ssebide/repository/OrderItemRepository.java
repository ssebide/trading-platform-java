package com.ssebide.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssebide.modal.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
