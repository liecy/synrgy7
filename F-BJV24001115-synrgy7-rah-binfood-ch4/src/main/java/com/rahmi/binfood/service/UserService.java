package com.rahmi.binfood.service;


import com.rahmi.binfood.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User addUser(User user);
    User updateUser(User user);
    void deleteUser(UUID userId);
    User getUserById(UUID userId);
    List<User> getAllUsers();
}