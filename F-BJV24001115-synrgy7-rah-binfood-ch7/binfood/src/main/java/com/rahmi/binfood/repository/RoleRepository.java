package com.rahmi.binfood.repository;

import com.rahmi.binfood.model.ERole;
import com.rahmi.binfood.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(ERole name);
}
