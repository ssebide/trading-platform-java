package com.ssebide.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssebide.modal.Coin;

public interface CoinRepository extends JpaRepository<Coin, String> {

}
