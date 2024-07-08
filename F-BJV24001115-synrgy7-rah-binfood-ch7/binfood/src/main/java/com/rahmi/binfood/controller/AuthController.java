package com.rahmi.binfood.controller;

import com.rahmi.binfood.dto.JwtResponseDTO;
import com.rahmi.binfood.dto.LoginRequestDTO;
import com.rahmi.binfood.dto.RegisterRequestDTO;
import com.rahmi.binfood.dto.VerifyOtpRequestDTO;
import com.rahmi.binfood.model.ERole;
import com.rahmi.binfood.model.Role;
import com.rahmi.binfood.model.User;
import com.rahmi.binfood.repository.RoleRepository;
import com.rahmi.binfood.repository.UserRepository;
import com.rahmi.binfood.security.JwtTokenUtil;
import com.rahmi.binfood.service.OtpService;
import com.rahmi.binfood.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl customUserDetailsService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Endpoint untuk registrasi
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        otpService.generateOTP(registerRequest.getEmailAddress());

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(ERole.ROLE_USER)));

        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .emailAddress(registerRequest.getEmailAddress())
                .roles(Collections.singleton(userRole))
                .otpValidated(false)
                .build();

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully, please check your email for OTP");
    }

    // Endpoint untuk validasi OTP
    @PostMapping("/validate-otp")
    public ResponseEntity<?> validateOtp(@RequestBody VerifyOtpRequestDTO otpValidationRequest) {
        if (otpService.validateOTP(otpValidationRequest.getEmailAddress(), otpValidationRequest.getOtp())) {
            User user = userRepository.findByEmailAddress(otpValidationRequest.getEmailAddress())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setOtpValidated(true);
            userRepository.save(user);
            return ResponseEntity.ok("OTP validated successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
    }

    // Endpoint untuk login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmailAddress(loginRequest.getEmailAddress())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isOtpValidated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OTP not validated");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(), loginRequest.getPassword())
            );

            final UserDetails userDetails = customUserDetailsService.loadUserByEmailAddress(loginRequest.getEmailAddress());
            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponseDTO(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // Endpoint untuk OAuth2 login
    @GetMapping("/oauth2/success")
    public ResponseEntity<?> getOauth2Success(HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("jwtToken");
        if (token == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token is null");
        }
        return ResponseEntity.ok(new JwtResponseDTO(token));
    }
}
