package com.rahmi.binfood.userservice.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    private String username;
    private String password;
    private String emailAddress;
    private List<OrderDTO> orders;
}
