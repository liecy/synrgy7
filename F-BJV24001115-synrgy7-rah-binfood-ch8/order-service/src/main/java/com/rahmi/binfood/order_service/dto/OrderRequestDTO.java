package com.rahmi.binfood.order_service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    private String destinationAddress;
    private Boolean completed;
    private List<OrderDetailDTO> orderDetails;
}
