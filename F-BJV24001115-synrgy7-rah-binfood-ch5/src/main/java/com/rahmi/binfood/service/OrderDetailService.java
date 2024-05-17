package com.rahmi.binfood.service;

import com.rahmi.binfood.dto.OrderDetailDTO;

import java.util.List;
import java.util.UUID;

public interface OrderDetailService {
    OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO);
    OrderDetailDTO updateOrderDetail(UUID id, OrderDetailDTO orderDetailDTO);
    OrderDetailDTO getOrderDetailById(UUID id);
    void deleteOrderDetail(UUID id);
    List<OrderDetailDTO> getAllOrderDetails();
}