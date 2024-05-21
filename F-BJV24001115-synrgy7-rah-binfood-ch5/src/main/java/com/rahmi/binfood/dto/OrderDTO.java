package com.rahmi.binfood.dto;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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