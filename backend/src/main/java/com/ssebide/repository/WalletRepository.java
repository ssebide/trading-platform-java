package com.ssebide.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssebide.modal.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findWalletByUserId(Long userId);
}
