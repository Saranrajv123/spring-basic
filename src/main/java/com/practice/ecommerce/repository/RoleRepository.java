package com.practice.ecommerce.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.ecommerce.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Role existsByRoleName(String roleName);

}
