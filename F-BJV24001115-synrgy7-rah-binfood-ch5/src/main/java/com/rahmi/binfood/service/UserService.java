package com.rahmi.binfood.service;


import com.rahmi.binfood.dto.UserDTO;
import com.rahmi.binfood.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDTO addUser(UserDTO userDTO);
    UserDTO updateUser(UUID id, UserDTO userDTO);
    UserDTO getUserById(UUID id);
    void deleteUser(UUID id);
}