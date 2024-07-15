package com.rahmi.binfood.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequestDTO {
    private String name;
    private Double price;
    private UUID merchantId;
}