package com.ssebide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssebide.domain.PaymentMethod;
import com.ssebide.modal.PaymentOrder;
import com.ssebide.modal.User;
import com.ssebide.response.PaymentResponse;
import com.ssebide.service.PaymentService;
import com.ssebide.service.UserService;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(@PathVariable PaymentMethod paymentMethod, @PathVariable Long amount, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        PaymentResponse paymentResponse;

        PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);

        if(paymentMethod.equals(PaymentMethod.STRIPE)){
            paymentResponse = paymentService.createStripePaymentLink(user, amount, amount);
        } else {
            throw new Exception("Pay using stripe..");
        }
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }
}
