package com.ssebide.service;

import java.util.List;

import com.ssebide.modal.Coin;

public interface CoinService {

    List<Coin> getCoinList(int page) throws Exception;

    String getMarketChat(String coinId, int days) throws Exception;

    String getCoinDetails(String coinId) throws Exception;

    Coin findById(String coinId) throws Exception;

    String searchCoin(String keyword) throws Exception;

    String getTop50CoinByMarketCapRank() throws Exception;

    String getTrendingCoins() throws Exception;
}
