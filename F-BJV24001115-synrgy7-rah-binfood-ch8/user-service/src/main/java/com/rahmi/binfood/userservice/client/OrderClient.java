package com.rahmi.binfood.userservice.client;


import com.rahmi.binfood.userservice.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "order-service", url = "http://localhost:8082")
public interface OrderClient {

    @GetMapping("/api/orders/user/{userId}")
    ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable("userId") UUID userId);
}