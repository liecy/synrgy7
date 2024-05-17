package com.rahmi.binfood.service;

import com.rahmi.binfood.dto.OrderDetailDTO;
import com.rahmi.binfood.exception.OrderDetailNotFoundException;
import com.rahmi.binfood.mapper.OrderDetailMapper;
import com.rahmi.binfood.model.OrderDetail;
import com.rahmi.binfood.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;

    @Autowired
    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, OrderDetailMapper orderDetailMapper) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderDetailMapper = orderDetailMapper;
    }

    @Override
    public OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailMapper.toEntity(orderDetailDTO);
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
        return orderDetailMapper.toDto(savedOrderDetail);
    }

    @Override
    public OrderDetailDTO updateOrderDetail(UUID id, OrderDetailDTO orderDetailDTO) {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new OrderDetailNotFoundException("OrderDetail not found with id: " + id));
        orderDetailMapper.updateFromDto(orderDetailDTO, existingOrderDetail);
        OrderDetail updatedOrderDetail = orderDetailRepository.save(existingOrderDetail);
        return orderDetailMapper.toDto(updatedOrderDetail);
    }

    @Override
    public OrderDetailDTO getOrderDetailById(UUID id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new OrderDetailNotFoundException("OrderDetail not found with id: " + id));
        return orderDetailMapper.toDto(orderDetail);
    }

    @Override
    public void deleteOrderDetail(UUID id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetailDTO> getAllOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        return orderDetails.stream()
                .map(orderDetailMapper::toDto)
                .collect(Collectors.toList());
    }
}