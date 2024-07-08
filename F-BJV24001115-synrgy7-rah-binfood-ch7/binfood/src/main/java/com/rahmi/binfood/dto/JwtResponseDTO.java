package com.rahmi.binfood.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponseDTO {
    private final String jwtToken;
}