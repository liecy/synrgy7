package com.rahmi.binfood.order_service.dto;

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
    private String productName;
    private int quantity;
    private double price;
    private double totalPrice;
}