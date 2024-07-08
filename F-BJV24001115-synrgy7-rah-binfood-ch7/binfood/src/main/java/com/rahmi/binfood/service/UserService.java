package com.rahmi.binfood.service;


import com.rahmi.binfood.dto.UserDTO;
import com.rahmi.binfood.dto.UserRequestDTO;

import java.util.UUID;

public interface UserService {
    UserDTO addUser(UserDTO userDTO);
    UserDTO updateUser(UUID id, UserRequestDTO userDTO);
    UserDTO getUserById(UUID id);
    void deleteUser(UUID id);
}
