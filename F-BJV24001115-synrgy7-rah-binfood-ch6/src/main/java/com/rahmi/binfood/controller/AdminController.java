package com.rahmi.binfood.controller;

import com.rahmi.binfood.model.ERole;
import com.rahmi.binfood.model.Role;
import com.rahmi.binfood.model.User;
import com.rahmi.binfood.repository.RoleRepository;
import com.rahmi.binfood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/assign-role")
    public ResponseEntity<?> assignRole(@RequestParam String username, @RequestParam ERole roleName) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOptional.get();
        Role role = roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));

        user.getRoles().add(role);
        userRepository.save(user);

        return ResponseEntity.ok("Role assigned successfully");
    }
}
