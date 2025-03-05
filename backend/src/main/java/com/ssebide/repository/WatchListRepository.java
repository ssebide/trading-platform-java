package com.ssebide.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssebide.modal.WatchList;

public interface WatchListRepository extends JpaRepository<WatchList, Long> {

    WatchList findByUserId(Long userId);
}
