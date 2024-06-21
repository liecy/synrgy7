package com.rahmi.binfood.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    private String username;
    private String emailAddress;
    private String password;
}
