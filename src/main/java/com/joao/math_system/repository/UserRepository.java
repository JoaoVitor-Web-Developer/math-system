package com.joao.math_system.repository;

import com.joao.math_system.entities.Role;
import com.joao.math_system.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByEmail(String name);
}
