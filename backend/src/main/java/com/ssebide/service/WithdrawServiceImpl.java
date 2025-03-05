
package com.ssebide.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssebide.domain.WithdrawStatus;
import com.ssebide.modal.User;
import com.ssebide.modal.Withdraw;
import com.ssebide.repository.WithdrawRepository;

@Service
public class WithdrawServiceImpl implements WithdrawService {

    @Autowired
    private WithdrawRepository withdrawRepository;

    @Override
    public Withdraw requestWithdraw(Long amount, User user) {
       Withdraw withdraw = new Withdraw();

       withdraw.setAmount(amount);
       withdraw.setUser(user);
       withdraw.setWithdrawStatus(WithdrawStatus.PENDING);

       return withdrawRepository.save(withdraw);
    }

    @Override
    public Withdraw proceedWithdraw(Long withdrawId, boolean accept) throws Exception {
        Optional<Withdraw> withdraw = withdrawRepository.findById(withdrawId);

        if(withdraw.isEmpty()){
            throw new Exception("Withdraw not found");
        }
        Withdraw withdraw1 = withdraw.get();
        withdraw1.setDate(LocalDateTime.now());

        if(accept){
            withdraw1.setWithdrawStatus(WithdrawStatus.SUCCESS);
        } else {
            withdraw1.setWithdrawStatus(WithdrawStatus.PENDING);
        }
        return withdrawRepository.save(withdraw1);
    }

    @Override
    public List<Withdraw> getUsersWithdrawHistory(User user) {
        return withdrawRepository.findByUserId(user.getId());
    }

    @Override
    public List<Withdraw> getAllWithdrawRequest() {
        return withdrawRepository.findAll();
    }

}
