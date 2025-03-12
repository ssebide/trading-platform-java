package com.ssebide.service;

import java.util.List;

import com.ssebide.domain.OrderType;
import com.ssebide.modal.Coin;
import com.ssebide.modal.Orders;
import com.ssebide.modal.OrderItem;
import com.ssebide.modal.User;

public interface OrderService {

    Orders createOrder(User user, OrderItem orderItem,OrderType orderType);

    Orders getOrderById(Long orderId) throws Exception;

    List<Orders> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol);

    Orders processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;

}
