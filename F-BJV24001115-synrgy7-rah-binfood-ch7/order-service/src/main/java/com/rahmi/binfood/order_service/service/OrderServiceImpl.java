package com.rahmi.binfood.order_service.service;

import com.rahmi.binfood.order_service.client.ProductClient;
import com.rahmi.binfood.order_service.dto.OrderDTO;
import com.rahmi.binfood.order_service.dto.OrderRequestDTO;
import com.rahmi.binfood.order_service.dto.ProductQuantityUpdateDTO;
import com.rahmi.binfood.order_service.exception.OrderNotFoundException;
import com.rahmi.binfood.order_service.mapper.OrderMapper;
import com.rahmi.binfood.order_service.model.Order;
import com.rahmi.binfood.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductClient productClient;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.productClient = productClient;
    }

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);

        // Update product quantities
        order.getOrderDetail().forEach(orderDetail -> {
            ProductQuantityUpdateDTO productQuantityUpdateDTO = new ProductQuantityUpdateDTO(orderDetail.getProductId(), orderDetail.getQuantity());
            productClient.updateProductQuantity(productQuantityUpdateDTO);
        });

        return orderMapper.toDto(savedOrder);
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(UUID userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(orderMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDTO updateOrder(UUID orderId, OrderRequestDTO orderDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
        orderMapper.updateFromDto(orderDTO, order);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
        orderRepository.delete(order);
    }
}
