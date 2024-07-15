package com.rahmi.binfood.order_service.client;

import com.rahmi.binfood.order_service.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "order-service")
public interface OrderClient {

    @GetMapping("/api/orders/user/{userId}")
    ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable("userId") UUID userId);
}
