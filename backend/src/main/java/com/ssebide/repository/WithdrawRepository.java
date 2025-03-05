package com.ssebide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssebide.modal.Withdraw;

public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {

    List<Withdraw> findByUserId(long userid);
}
