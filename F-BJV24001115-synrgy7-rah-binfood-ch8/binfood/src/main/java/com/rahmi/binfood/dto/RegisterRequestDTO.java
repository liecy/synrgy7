package com.rahmi.binfood.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterRequestDTO {
    private String username;
    private String emailAddress;
    private String password;
}
