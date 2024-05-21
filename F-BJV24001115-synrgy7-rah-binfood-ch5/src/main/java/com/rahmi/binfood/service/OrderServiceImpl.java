package com.rahmi.binfood.service;

import com.rahmi.binfood.dto.OrderDTO;
import com.rahmi.binfood.dto.OrderDetailDTO;
import com.rahmi.binfood.exception.OrderNotFoundException;
import com.rahmi.binfood.mapper.OrderMapper;
import com.rahmi.binfood.model.Order;
import com.rahmi.binfood.model.OrderDetail;
import com.rahmi.binfood.model.Product;
import com.rahmi.binfood.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, ProductService productService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.productService = productService;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        // Save order to get the generated ID
        Order savedOrder = orderRepository.save(order);

        // Update order details with the saved order reference
        for (OrderDetail detail : savedOrder.getOrderDetail()) {
            detail.setOrder(savedOrder);
            Product product = productService.getProductById(detail.getProduct().getId());
            detail.setTotalPrice(product.getPrice() * detail.getQuantity());
        }

        savedOrder = orderRepository.save(savedOrder);

//        savedOrder.setCompleted(true);
//        orderRepository.save(savedOrder);

        return orderMapper.toDto(savedOrder);
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(UUID userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(order -> {
                    OrderDTO orderDTO = orderMapper.toDto(order);
                    List<OrderDetailDTO> orderDetails = order.getOrderDetail().stream()
                            .map(orderMapper::mapToOrderDetailDTO)
                            .collect(Collectors.toList());
                    orderDTO.setOrderDetails(orderDetails);
                    return orderDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
        return orderMapper.toDto(order);
    }
}