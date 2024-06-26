package com.rahmi.binfood.controller;

import com.rahmi.binfood.dto.JwtResponseDTO;
import com.rahmi.binfood.dto.LoginRequestDTO;
import com.rahmi.binfood.dto.RegisterRequestDTO;
import com.rahmi.binfood.model.ERole;
import com.rahmi.binfood.model.Role;
import com.rahmi.binfood.model.User;
import com.rahmi.binfood.repository.RoleRepository;
import com.rahmi.binfood.repository.UserRepository;
import com.rahmi.binfood.security.JwtTokenUtil;
import com.rahmi.binfood.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetailsServiceImpl customUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        LoginRequestDTO loginRequest = new LoginRequestDTO("username", "password");
        UserDetails userDetails = mock(UserDetails.class);
        String token = "dummyToken";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(customUserDetailsService.loadUserByUsername("username")).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn(token);

        ResponseEntity<?> response = authController.login(loginRequest);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(token, ((JwtResponseDTO) response.getBody()).getJwtToken());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(customUserDetailsService, times(1)).loadUserByUsername("username");
        verify(jwtTokenUtil, times(1)).generateToken(userDetails);
    }

    @Test
    void testLogin_InvalidCredentials() {
        LoginRequestDTO loginRequest = new LoginRequestDTO("username", "wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        try {
            authController.login(loginRequest);
        } catch (RuntimeException e) {
            assertEquals("Invalid credentials", e.getMessage());
        }

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(customUserDetailsService, never()).loadUserByUsername(anyString());
        verify(jwtTokenUtil, never()).generateToken(any(UserDetails.class));
    }

    @Test
    void testRegister_UserAlreadyExists() {
        RegisterRequestDTO registerRequest = new RegisterRequestDTO("username", "email@example.com", "password");

        when(userRepository.existsByUsername("username")).thenReturn(true);

        ResponseEntity<?> response = authController.register(registerRequest);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Username is already taken", response.getBody());

        verify(userRepository, times(1)).existsByUsername("username");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegister_Success() {
        RegisterRequestDTO registerRequest = new RegisterRequestDTO("username", "email@example.com", "password");
        Role role = new Role(ERole.ROLE_USER);
        User user = new User();
        user.setUsername("username");
        user.setEmailAddress("email@example.com");
        user.setPassword("encodedPassword");
        user.setRoles(Collections.singleton(role));

        when(userRepository.existsByUsername("username")).thenReturn(false);
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Capture the User object passed to userRepository.save()
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        ResponseEntity<?> response = authController.register(registerRequest);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("User registered successfully", response.getBody());

        verify(userRepository, times(1)).existsByUsername("username");
        verify(roleRepository, times(1)).findByName(ERole.ROLE_USER);
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(userCaptor.capture());

        // Verify the captured User object
        User capturedUser = userCaptor.getValue();
        assertEquals("username", capturedUser.getUsername());
        assertEquals("email@example.com", capturedUser.getEmailAddress());
        assertEquals("encodedPassword", capturedUser.getPassword());
        assertEquals(1, capturedUser.getRoles().size());
        assertEquals(role, capturedUser.getRoles().iterator().next());
    }
}
