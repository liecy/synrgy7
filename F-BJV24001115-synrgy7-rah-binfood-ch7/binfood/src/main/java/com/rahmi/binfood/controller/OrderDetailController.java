package com.rahmi.binfood.controller;

import com.rahmi.binfood.dto.OrderDetailDTO;
import com.rahmi.binfood.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @PostMapping
    public ResponseEntity<OrderDetailDTO> addOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetailDTO createdOrderDetail = orderDetailService.addOrderDetail(orderDetailDTO);
        return new ResponseEntity<>(createdOrderDetail, HttpStatus.CREATED);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderDetailDTO>> getOrderDetailsByOrderId(@PathVariable UUID orderId) {
        List<OrderDetailDTO> orderDetails = orderDetailService.getOrderDetailsByOrderId(orderId);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }
}
