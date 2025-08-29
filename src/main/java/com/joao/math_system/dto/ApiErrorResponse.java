package com.joao.math_system.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiErrorResponse(
	String message,
	HttpStatus status,
	int statusCode,
	LocalDateTime timestamp
) {}
