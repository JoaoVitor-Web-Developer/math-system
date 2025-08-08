package com.joao.math_system.repository;

import com.joao.math_system.entities.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BudgetsRepository extends JpaRepository<Budget, UUID> {
	List<Budget> findByUserId(UUID userId);
}
