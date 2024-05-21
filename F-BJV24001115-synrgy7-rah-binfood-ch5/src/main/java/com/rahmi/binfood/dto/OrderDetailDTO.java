package com.rahmi.binfood.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private UUID id;
    private UUID orderId;
    private UUID productId;
    private Integer quantity;
    private Double totalPrice;
    private String productName;
    private Double price;
}