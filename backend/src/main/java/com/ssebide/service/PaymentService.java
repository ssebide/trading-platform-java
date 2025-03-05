package com.ssebide.service;

import com.ssebide.domain.PaymentMethod;
import com.ssebide.modal.PaymentOrder;
import com.ssebide.modal.User;
import com.ssebide.response.PaymentResponse;

public interface PaymentService {

    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id)throws Exception;

    Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws Exception;  

    PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws Exception;
}
