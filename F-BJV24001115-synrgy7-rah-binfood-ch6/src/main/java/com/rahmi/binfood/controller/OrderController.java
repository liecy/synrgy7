package com.rahmi.binfood.controller;

import com.rahmi.binfood.dto.OrderDTO;
import com.rahmi.binfood.dto.OrderRequestDTO;
import com.rahmi.binfood.service.OrderService;
import com.rahmi.binfood.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return ApiResponse.success(HttpStatus.CREATED, "Order created successfully", createdOrder, "order");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getOrdersByUserId(@PathVariable UUID userId) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        return ApiResponse.success(HttpStatus.OK, "Orders retrieved successfully", orders, "orders");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Object> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ApiResponse.success(HttpStatus.OK, "All orders retrieved successfully", orders, "orders");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}")
    public ResponseEntity<Object> getOrderById(@PathVariable UUID orderId) {
        OrderDTO order = orderService.getOrderById(orderId);
        return ApiResponse.success(HttpStatus.OK, "Order retrieved successfully", order, "orders");
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{orderId}")
    public ResponseEntity<Object> updateOrder(@PathVariable UUID orderId, @RequestBody OrderRequestDTO orderDTO) {
        OrderDTO updatedOrder = orderService.updateOrder(orderId, orderDTO);
        return ApiResponse.success(HttpStatus.OK, "Order updated successfully", updatedOrder);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Object> deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        return ApiResponse.success(HttpStatus.OK, "Order deleted successfully", null);
    }

}