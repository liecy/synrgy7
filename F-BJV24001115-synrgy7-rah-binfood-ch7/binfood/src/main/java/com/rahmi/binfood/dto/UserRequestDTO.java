package com.rahmi.binfood.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private String username;
    private String emailAddress;
    private String password;
}