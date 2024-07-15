package com.rahmi.binfood.userservice.service;

import com.rahmi.binfood.userservice.client.OrderClient;
import com.rahmi.binfood.userservice.dto.UserDTO;
import com.rahmi.binfood.userservice.dto.UserRequestDTO;
import com.rahmi.binfood.userservice.dto.OrderDTO;
import com.rahmi.binfood.userservice.exception.UserNotFoundException;
import com.rahmi.binfood.userservice.mapper.UserMapper;
import com.rahmi.binfood.userservice.model.User;
import com.rahmi.binfood.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final OrderClient orderClient;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, OrderClient orderClient) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.orderClient = orderClient;
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UUID id, UserRequestDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userMapper.updateFromDTO(userDTO, existingUser);
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);
    }

    @Override
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        UserDTO userDTO = userMapper.toDTO(user);

        // Using FeignClient to get orders
        ResponseEntity<List<OrderDTO>> orderResponse = orderClient.getOrdersByUserId(id);
        if (orderResponse.getStatusCode().is2xxSuccessful()) {
            userDTO.setOrders(orderResponse.getBody());
        }

        return userDTO;
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
