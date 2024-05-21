package com.rahmi.binfood.service;

import com.rahmi.binfood.dto.OrderDTO;
import com.rahmi.binfood.model.Order;

import java.util.List;
import java.util.UUID;

//public interface OrderService {
//    Order createOrder(Order order);
//    List<Order> getOrdersByUserId(UUID userId);
//    List<Order> getAllOrders();
//}

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);
    List<OrderDTO> getOrdersByUserId(UUID userId);
    List<OrderDTO> getAllOrders();
    OrderDTO getOrderById(UUID orderId);
}