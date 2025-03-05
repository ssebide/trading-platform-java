package com.ssebide.service;

import com.ssebide.modal.PaymentDetails;
import com.ssebide.modal.User;

public interface PaymentDetailsService {

    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolder, String ifsc, String bankName, User user);

    public PaymentDetails getUsersPaymentDetails(User user);
}
