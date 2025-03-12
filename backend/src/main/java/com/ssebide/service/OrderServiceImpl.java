package com.ssebide.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssebide.domain.OrderStatus;
import com.ssebide.domain.OrderType;
import com.ssebide.modal.Asset;
import com.ssebide.modal.Coin;
import com.ssebide.modal.Orders;
import com.ssebide.modal.OrderItem;
import com.ssebide.modal.User;
import com.ssebide.repository.OrderItemRepository;
import com.ssebide.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private AssetService assetService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Orders getOrderById(Long orderId) throws Exception {
        
        return orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
    }

    @Override
    public Orders createOrder(User user, OrderItem orderItem, OrderType orderType) {
        
        double price = orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();

        Orders order = new Orders();

        order.setUser(user);
        order.setOrderItem(orderItem);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimestamp(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    @Override
    public List<Orders> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {
       
        return orderRepository.findByUserId(userId);
    }


    private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);

        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public Orders buyAsset(Coin coin, double quantity, User user) throws Exception{
        if(quantity<=0){
            throw new Exception("Quantity should be greater than zero");
        }

        double buyPrice = coin.getCurrentPrice();

        OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, 0);

        Orders order = createOrder(user, orderItem, OrderType.BUY);

        orderItem.setOrder(order);

        walletService.payOrderPayment(order, user);

        order.setStatus(OrderStatus.SUCCESS);

        order.setOrderType(OrderType.BUY);

        Orders savedOrder = orderRepository.save(order);

        //create asset
        Asset oldAsset = assetService.findAssetByUserIdAndCoinId(order.getUser().getId(), order.getOrderItem().getCoin().getId());

        if(oldAsset == null){
            assetService.createAsset(user, orderItem.getCoin(), orderItem.getQuantity());
        } else {
            assetService.updateAsset(oldAsset.getId(), quantity);
        }
        return savedOrder;
    }


    @Transactional
    public Orders sellAsset(Coin coin, double quantity, User user) throws Exception{
        if(quantity<=0){
            throw new Exception("Quantity should be greater than zero");
        }

        double sellPrice = coin.getCurrentPrice();

        Asset assetToSell = assetService.findAssetByUserIdAndCoinId(user.getId(), coin.getId());

        double buyPrice = assetToSell.getBuyPrice();
        if(assetToSell!=null){      
            OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);

            Orders order = createOrder(user, orderItem, OrderType.SELL);
        
        orderItem.setOrder(order);

        if(assetToSell.getQuantity()>=quantity){

            order.setStatus(OrderStatus.SUCCESS);
            order.setOrderType(OrderType.SELL);
            Orders savedOrder = orderRepository.save(order);

            walletService.payOrderPayment(order, user);

            Asset updatedAsset = assetService.updateAsset(assetToSell.getId(), -quantity);

            if(updatedAsset.getQuantity()*coin.getCurrentPrice()<=1){
                assetService.deleteAssets(updatedAsset.getId());
            }
            return savedOrder;
        } 
        throw new Exception("Unsufficient quantity to sell");
    }
    throw new Exception("Asset not found");
    
}

    @Override
    @Transactional
    public Orders processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {
        
        if(orderType.equals(OrderType.BUY)){
            return buyAsset(coin, quantity, user); 
        } else if(orderType.equals(OrderType.SELL)) {
            return sellAsset(coin, quantity, user);
        } else {
            throw new Exception("Invalid Order Type");
        }
    }

}
