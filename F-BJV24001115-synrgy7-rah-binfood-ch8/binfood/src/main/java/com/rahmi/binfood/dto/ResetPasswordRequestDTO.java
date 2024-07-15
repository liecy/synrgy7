package com.rahmi.binfood.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequestDTO {
    private String emailAddress;
    private String otp;
    private String newPassword;
}