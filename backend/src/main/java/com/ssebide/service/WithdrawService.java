package com.ssebide.service;

import java.util.List;

import com.ssebide.modal.User;
import com.ssebide.modal.Withdraw;

public interface WithdrawService {


    Withdraw requestWithdraw(Long amount, User user);

    Withdraw proceedWithdraw(Long withdrawId, boolean accept) throws Exception;

    List<Withdraw> getUsersWithdrawHistory(User user);

    List<Withdraw> getAllWithdrawRequest();
}
