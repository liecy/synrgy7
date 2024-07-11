package com.rahmi.binfood.order_service.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private UUID id;
    private Date createdDate;
    private String destinationAddress;
    private UUID userId;
    private Boolean completed;
    private List<OrderDetailDTO> orderDetails;
}