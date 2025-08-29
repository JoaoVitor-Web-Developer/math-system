package com.joao.math_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class AuthDTO {
	public record LoginRequestDTO(
			@NotBlank @Email String email,
			@NotBlank String password
	) {}

	public record UserRegisterDTO(
			@NotBlank @Size(min = 3) String name,
			@NotBlank @Email String email,
			@NotBlank @Size(min = 6) String password
	) {}

	public record AuthResponseDTO(
			String accessToken,
			String refreshToken
	) {}
	
	public record RefreshTokenRequestDTO(
			@NotBlank String refreshToken
	) {}
}
