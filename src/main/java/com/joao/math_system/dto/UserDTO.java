package com.joao.math_system.dto;

import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class UserDTO {
	public record UserUpdateDTO (
			String name,
			String password,
			String profileImageUrl,
			BigDecimal pricePerSquareMeter
	) {}
}
