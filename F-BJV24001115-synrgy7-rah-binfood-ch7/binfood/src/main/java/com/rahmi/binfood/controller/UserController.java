package com.rahmi.binfood.controller;

import com.rahmi.binfood.dto.UserDTO;
import com.rahmi.binfood.dto.UserRequestDTO;
import com.rahmi.binfood.service.UserService;
import com.rahmi.binfood.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Object> addUser(@RequestBody UserDTO userDTO) {
        UserDTO newUserDTO = userService.addUser(userDTO);
        return ApiResponse.success(HttpStatus.CREATED, "User has been successfully added", newUserDTO, "user");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable UUID id, @RequestBody UserRequestDTO userDTO) {
        UserDTO updatedUserDTO = userService.updateUser(id, userDTO);
        return ApiResponse.success(HttpStatus.OK, "User has been successfully updated", updatedUserDTO, "user");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable UUID id) {
        UserDTO userDTO = userService.getUserById(id);
        return ApiResponse.success(HttpStatus.OK, "User has been successfully retrieved", userDTO, "user");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ApiResponse.success(HttpStatus.OK, "User has been successfully deleted", null);
    }
}