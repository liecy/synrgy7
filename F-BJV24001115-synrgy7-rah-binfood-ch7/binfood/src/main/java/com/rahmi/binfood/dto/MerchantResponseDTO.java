package com.rahmi.binfood.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantResponseDTO {
    private UUID id;
    private String name;
    private String location;
    private Boolean open;
}