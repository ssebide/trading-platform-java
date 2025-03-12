package com.ssebide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssebide.modal.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByUserId(Long userId);
}
