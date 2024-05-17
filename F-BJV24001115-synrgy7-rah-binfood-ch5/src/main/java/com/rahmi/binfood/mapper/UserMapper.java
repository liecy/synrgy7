package com.rahmi.binfood.mapper;

import com.rahmi.binfood.dto.UserDTO;
import com.rahmi.binfood.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO toDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User toEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public void updateFromDTO(UserDTO userDTO, User user) {
        UUID id = user.getId();
        modelMapper.map(userDTO, user);
    }
}