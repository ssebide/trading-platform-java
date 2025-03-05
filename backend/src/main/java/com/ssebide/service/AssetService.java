package com.ssebide.service;

import java.util.List;

import com.ssebide.modal.Asset;
import com.ssebide.modal.Coin;
import com.ssebide.modal.User;

public interface AssetService {

    Asset createAsset(User user, Coin coin, double quantity);

    Asset getAssetById(Long assetid) throws Exception;

    Asset getAssetByUserIdAndId(Long userId, Long assetId);

    List<Asset> getUserAssets(Long userId);

    Asset updateAsset(Long assetId, double quantity) throws Exception;

    Asset findAssetByUserIdAndCoinId(Long userId, String coinId);

    void deleteAssets(Long assetId);
}
