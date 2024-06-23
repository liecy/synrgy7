package com.rahmi.binfood.controller;

import com.rahmi.binfood.model.ERole;
import com.rahmi.binfood.model.Role;
import com.rahmi.binfood.model.User;
import com.rahmi.binfood.repository.RoleRepository;
import com.rahmi.binfood.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdminControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAssignRole_UserNotFound() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());

        ResponseEntity<?> response = adminController.assignRole("username", ERole.ROLE_USER);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("User not found", response.getBody());
    }

    @Test
    void testAssignRole_RoleAssignedSuccessfully() {
        User user = new User();
        user.setRoles(new HashSet<>()); // Inisialisasi roles di sini
        Role role = new Role(ERole.ROLE_USER);

        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<?> response = adminController.assignRole("username", ERole.ROLE_USER);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Role assigned successfully", response.getBody());

        verify(userRepository, times(1)).findByUsername("username");
        verify(roleRepository, times(1)).findByName(ERole.ROLE_USER);
        verify(userRepository, times(1)).save(user);
    }
}
