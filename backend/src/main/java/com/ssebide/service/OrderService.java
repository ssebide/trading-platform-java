package com.ssebide.service;

import java.util.List;

import com.ssebide.domain.OrderType;
import com.ssebide.modal.Coin;
import com.ssebide.modal.Order;
import com.ssebide.modal.OrderItem;
import com.ssebide.modal.User;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem,OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;

}
