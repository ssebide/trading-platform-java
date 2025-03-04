package com.ssebide.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssebide.domain.OrderType;
import com.ssebide.modal.Order;
import com.ssebide.modal.User;
import com.ssebide.modal.Wallet;
import com.ssebide.repository.WalletRepository;

@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet = walletRepository.findWalletByUserId(user.getId());

        if(wallet==null){
            wallet = new Wallet();
            wallet.setUser(user);
        }
        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long money) {
        BigDecimal balance = wallet.getBalance();

        BigDecimal newBalance = balance.add(BigDecimal.valueOf(money));

        wallet.setBalance(newBalance);

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletByid(Long id) throws Exception {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isPresent()){
            return wallet.get();
        }
        throw new Exception("wallet not found");
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception {
        Wallet senderWallet = getUserWallet(sender);

        if(senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0){
            throw new Exception("Insufficient balance...");
        }
        BigDecimal senderBalance = senderWallet.getBalance().subtract(BigDecimal.valueOf(amount));

        senderWallet.setBalance(senderBalance);

        walletRepository.save(senderWallet);

        BigDecimal receiverBalance = receiverWallet.getBalance().add(BigDecimal.valueOf(amount));

        receiverWallet.setBalance(receiverBalance);

        walletRepository.save(receiverWallet);

        return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) {
        Wallet wallet = getUserWallet(user);

        

        return null;
    }

}
