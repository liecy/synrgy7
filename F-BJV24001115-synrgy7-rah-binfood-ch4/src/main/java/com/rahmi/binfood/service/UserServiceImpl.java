package com.rahmi.binfood.service;

import com.rahmi.binfood.exception.UserNotFoundException;
import com.rahmi.binfood.model.User;
import com.rahmi.binfood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        // logika validasi / pemrosesan tambahan sebelum menyimpan user
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        // mastiin user yang diupdate udah ada di database
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}