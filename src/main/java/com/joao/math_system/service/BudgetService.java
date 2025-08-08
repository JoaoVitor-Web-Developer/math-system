package com.joao.math_system.service;

import com.joao.math_system.dto.BudgetsDTO.*;
import com.joao.math_system.entities.Budget;
import com.joao.math_system.entities.User;
import com.joao.math_system.repository.BudgetsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {

	private final BudgetsRepository budgetsRepository;

	public List<BudgetResponseDTO> getUserBudgets() {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return budgetsRepository.findByUserId(currentUser.getId()).stream()
		                       .map(this::toBudgetResponseDTO)
		                       .collect(Collectors.toList());
	}

	@Transactional
	public BudgetResponseDTO createBudget(BudgetRequestDTO request) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		BigDecimal pricePerMeter = user.getPricePerSquareMeter();
		if (pricePerMeter == null || pricePerMeter.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalStateException("User does not have a valid price per square meter configured.");
		}

		BigDecimal totalArea = request.dimensionX().multiply(request.dimensionY());
		BigDecimal totalPrice = totalArea.multiply(pricePerMeter);

		Budget budget = new Budget();
		budget.setUser(user);
		budget.setClientName(request.clientName());
		budget.setDescription(request.description());
		budget.setTotalArea(totalArea);
		budget.setPricePerMeterAtCreation(pricePerMeter);
		budget.setTotalPrice(totalPrice);
		budget.setCreatedAt(LocalDateTime.now());

		Budget savedBudget = budgetsRepository.save(budget);

		return toBudgetResponseDTO(savedBudget);
	}


	private BudgetResponseDTO toBudgetResponseDTO(Budget budget) {
		return new BudgetResponseDTO(
				budget.getId(),
				budget.getClientName(),
				budget.getDescription(),
				budget.getTotalArea(),
				budget.getPricePerMeterAtCreation(),
				budget.getTotalPrice(),
				budget.getCreatedAt(),
				budget.getUser().getId()
		);
	}
}
