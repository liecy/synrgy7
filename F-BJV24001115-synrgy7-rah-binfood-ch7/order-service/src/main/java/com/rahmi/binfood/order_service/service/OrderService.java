package com.rahmi.binfood.order_service.service;

import com.rahmi.binfood.order_service.dto.OrderDTO;
import com.rahmi.binfood.order_service.dto.OrderRequestDTO;

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

