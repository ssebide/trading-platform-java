package com.ssebide.service;

import com.ssebide.modal.Coin;
import com.ssebide.modal.User;
import com.ssebide.modal.WatchList;

public interface WatchListService {

    WatchList findUserWatchList(Long userId) throws Exception;
    WatchList createWatchList(User user);
    WatchList findById(Long id) throws Exception;

    Coin addItemToWatchList(Coin coin, User user) throws Exception;
}
