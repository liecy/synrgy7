package com.rahmi.binfood.service;

import com.rahmi.binfood.dto.OrderDetailDTO;
import com.rahmi.binfood.exception.OrderDetailNotFoundException;
import com.rahmi.binfood.exception.ProductNotFoundException;
import com.rahmi.binfood.mapper.OrderDetailMapper;
import com.rahmi.binfood.model.OrderDetail;
import com.rahmi.binfood.model.Product;
import com.rahmi.binfood.repository.OrderDetailRepository;
import com.rahmi.binfood.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;
    private final ProductRepository productRepository;

    @Autowired
    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, OrderDetailMapper orderDetailMapper, ProductRepository productRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderDetailMapper = orderDetailMapper;
        this.productRepository = productRepository;
    }

    @Override
    public OrderDetailDTO addOrderDetail(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailMapper.toEntity(orderDetailDTO);
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + orderDetailDTO.getProductId()));
        orderDetail.setTotalPrice(product.getPrice() * orderDetailDTO.getQuantity());
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
        return orderDetailMapper.toDto(savedOrderDetail);
    }

    @Override
    public List<OrderDetailDTO> getOrderDetailsByOrderId(UUID orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        return orderDetails.stream()
                .map(orderDetailMapper::toDto)
                .collect(Collectors.toList());
    }
}