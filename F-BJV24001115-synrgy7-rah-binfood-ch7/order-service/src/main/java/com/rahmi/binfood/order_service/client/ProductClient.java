package com.rahmi.binfood.order_service.client;

import com.rahmi.binfood.order_service.dto.ProductUpdateRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service")
public interface ProductClient {

    @PutMapping("/api/products/update-quantity")
    void updateProductQuantity(@RequestBody ProductUpdateRequestDTO productUpdateRequestDTO);
}
