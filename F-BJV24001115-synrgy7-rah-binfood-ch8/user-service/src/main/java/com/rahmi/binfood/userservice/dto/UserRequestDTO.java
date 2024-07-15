package com.rahmi.binfood.userservice.dto;

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
