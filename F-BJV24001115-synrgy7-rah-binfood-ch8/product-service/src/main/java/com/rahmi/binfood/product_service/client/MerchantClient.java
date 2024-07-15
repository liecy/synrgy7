package com.rahmi.binfood.product_service.client;

import com.rahmi.binfood.product_service.dto.MerchantResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "merchant-service", url = "http://localhost:8081") // Update the URL to the actual location of your MerchantService
public interface MerchantClient {
    @GetMapping("/api/merchants/{id}")
    MerchantResponseDTO getMerchantById(@PathVariable UUID id);
}

