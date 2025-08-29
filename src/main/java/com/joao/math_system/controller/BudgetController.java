package com.joao.math_system.controller;

import com.joao.math_system.dto.BudgetsDTO.*;
import com.joao.math_system.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

	private final BudgetService budgetService;

	@GetMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<BudgetResponseDTO>> getUserBudgets() {
		return ResponseEntity.ok(budgetService.getUserBudgets());
	}

	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> getAllBudgetsForAdmin() {

		return ResponseEntity.ok("Admin access successful!");
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<BudgetResponseDTO> createBudget(@Valid @RequestBody BudgetRequestDTO request) {
		BudgetResponseDTO createdBudget = budgetService.createBudget(request);
		URI location = URI.create(String.format("/api/budgets/%s", createdBudget.id()));
		return ResponseEntity.created(location).body(createdBudget);
	}
}
