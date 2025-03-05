package com.ssebide.service;

import com.ssebide.modal.Order;
import com.ssebide.modal.User;
import com.ssebide.modal.Wallet;

public interface WalletService {


    Wallet getUserWallet(User user);

    Wallet addBalance(Wallet wallet, Long money);

    Wallet findWalletByid(Long id) throws Exception;

    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception;

    Wallet payOrderPayment(Order order, User user) throws Exception;
}
