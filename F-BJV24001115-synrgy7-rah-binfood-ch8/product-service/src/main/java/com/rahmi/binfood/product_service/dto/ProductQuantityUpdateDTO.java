package com.rahmi.binfood.product_service.dto;import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantityUpdateDTO {
    private UUID productId;
    private Integer quantity;
}