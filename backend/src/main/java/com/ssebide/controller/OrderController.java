package com.ssebide.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssebide.domain.OrderType;
import com.ssebide.modal.Coin;
import com.ssebide.modal.Order;
import com.ssebide.modal.User;
import com.ssebide.request.CreateOrderRequest;
import com.ssebide.service.CoinService;
import com.ssebide.service.OrderService;
import com.ssebide.service.UserService;
import com.ssebide.service.WalletTransactionService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

    @Autowired
    private WalletTransactionService walletTransactionService;

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(@RequestHeader("Authorization") String jwt, @RequestBody CreateOrderRequest req) throws Exception{

        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(req.getCoinId());

        Order order = orderService.processOrder(coin, req.getQuantity(), req.getOrderType(), user);

        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@RequestHeader("Authorization") String jwtToken, @PathVariable Long orderId) throws Exception{
        if(jwtToken == null){
            throw new Exception("Token missing...");
        }

        User user = userService.findUserProfileByJwt(jwtToken);

        Order order = orderService.getOrderById(orderId);
        if(order.getUser().getId().equals(user.getId())){
            return ResponseEntity.ok(order);
        } else {
            //return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            throw new Exception("You dont have access...");
        }
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrdersForUser(@RequestHeader("Authorization") String jwt, @RequestParam(required = false) OrderType order_type, @RequestParam(required = false) String asset_symbol) throws Exception{
        Long userId = userService.findUserProfileByJwt(jwt).getId();

        List<Order> useOrders = orderService.getAllOrdersOfUser(userId, order_type, asset_symbol);

        return ResponseEntity.ok(useOrders);
    }
}
