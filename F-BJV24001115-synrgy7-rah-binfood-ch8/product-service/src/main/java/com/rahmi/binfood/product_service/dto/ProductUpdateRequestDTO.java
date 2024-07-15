package com.rahmi.binfood.product_service.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequestDTO {
    private String name;
    private Double price;
    private UUID merchantId;
}