package com.ssebide.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ssebide.modal.User;
import com.ssebide.modal.Wallet;
import com.ssebide.modal.Withdraw;
import com.ssebide.service.UserService;
import com.ssebide.service.WalletService;
import com.ssebide.service.WalletTransactionService;
import com.ssebide.service.WithdrawService;

@RestController
public class WithdrawController {

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private WalletTransactionService walletTransactionService;

    @PostMapping("/api/withdraw/{amount}")
    public ResponseEntity<?> withdrawRequest(@PathVariable Long amount, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);

        Withdraw withdraw = withdrawService.requestWithdraw(amount, user);
        walletService.addBalance(wallet, -withdraw.getAmount());

        //wallet transaction

        return new ResponseEntity<>(withdraw, HttpStatus.OK);

    }

    @PatchMapping("/api/admin/withdraw/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdraw(@PathVariable Long id, @PathVariable boolean accept, @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);

        Withdraw withdraw = withdrawService.proceedWithdraw(id, accept);

        Wallet userWallet = walletService.getUserWallet(user);

        if(!accept){
            walletService.addBalance(userWallet, withdraw.getAmount());
        }
        return new ResponseEntity<>(withdraw, HttpStatus.OK);
    }

    @GetMapping("/api/withdraw")
    public ResponseEntity<List<Withdraw>> getWithdrawHistory(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        List<Withdraw> withdraws = withdrawService.getUsersWithdrawHistory(user);

        return new ResponseEntity<>(withdraws, HttpStatus.OK);
    }

    @GetMapping("/api/admin/withdraw")
    public ResponseEntity<List<Withdraw>> getAllWithdrawRequest(@RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);

        List<Withdraw> withdraw = withdrawService.getAllWithdrawRequest();

        return new ResponseEntity<>(withdraw, HttpStatus.OK);
    }

}
