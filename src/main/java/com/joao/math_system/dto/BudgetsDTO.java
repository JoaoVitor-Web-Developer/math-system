package com.joao.math_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public final class BudgetsDTO {

	public record BudgetRequestDTO(
			@NotNull @Positive BigDecimal dimensionX,
			@NotNull @Positive BigDecimal dimensionY,
			@NotBlank String clientName,
			String description
	) {}

	public record BudgetResponseDTO(
			UUID id,
			String clientName,
			String description,
			BigDecimal totalArea,
			BigDecimal pricePerMeterAtCreation,
			BigDecimal totalPrice,
			LocalDateTime createdAt,
			UUID userId
	) {}
}
