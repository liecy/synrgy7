package com.rahmi.binfood.service;

import com.rahmi.binfood.dto.OrderDetailDTO;

import java.util.List;
import java.util.UUID;

public interface OrderDetailService {
    OrderDetailDTO addOrderDetail(OrderDetailDTO orderDetailDTO);
    List<OrderDetailDTO> getOrderDetailsByOrderId(UUID orderId);
}