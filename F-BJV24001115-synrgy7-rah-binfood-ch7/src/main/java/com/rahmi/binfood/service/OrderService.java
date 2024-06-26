package com.rahmi.binfood.service;

import com.rahmi.binfood.dto.OrderDTO;
import com.rahmi.binfood.dto.OrderRequestDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);
    List<OrderDTO> getOrdersByUserId(UUID userId);
    List<OrderDTO> getAllOrders();
    OrderDTO getOrderById(UUID orderId);
    OrderDTO updateOrder(UUID orderId, OrderRequestDTO orderDTO);
    void deleteOrder(UUID orderId);
}