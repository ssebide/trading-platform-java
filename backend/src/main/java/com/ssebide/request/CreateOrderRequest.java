package com.ssebide.request;

import com.ssebide.domain.OrderType;

import lombok.Data;

@Data
public class CreateOrderRequest {

    private String coinId;
    private double quantity;
    private OrderType orderType;
}
