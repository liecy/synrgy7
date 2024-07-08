package com.rahmi.binfood.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyOtpRequestDTO {
    private String emailAddress;
    private String otp;
}
