package com.rahmi.binfood.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private UUID id;
    private String name;
    private Double price;
    private UUID merchantId;
}