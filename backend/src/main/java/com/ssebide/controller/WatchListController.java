package com.ssebide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssebide.modal.Coin;
import com.ssebide.modal.User;
import com.ssebide.modal.WatchList;
import com.ssebide.service.CoinService;
import com.ssebide.service.UserService;
import com.ssebide.service.WatchListService;

@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {

    @Autowired
    private WatchListService watchListService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<WatchList> getUserWatchList(@RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);

        WatchList watchList = watchListService.findUserWatchList(user.getId());

        return ResponseEntity.ok(watchList);
    }

    @GetMapping("/{watchlistId}")
    public ResponseEntity<WatchList> getWatchListById(@PathVariable Long watchlistId) throws Exception {
        WatchList watchList = watchListService.findById(watchlistId);
        return ResponseEntity.ok(watchList);
    }

    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchList(@RequestHeader("Authorization") String jwt, @PathVariable String coinId) throws Exception{

        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(coinId);
        Coin addedCoin = watchListService.addItemToWatchList(coin, user);
        return ResponseEntity.ok(addedCoin);
    }
}
