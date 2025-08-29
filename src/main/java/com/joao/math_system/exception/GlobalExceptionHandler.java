package com.joao.math_system.exception;

import com.joao.math_system.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
		ApiErrorResponse errorResponse = new ApiErrorResponse(
				e.getMessage(),
				HttpStatus.BAD_REQUEST,
				HttpStatus.BAD_REQUEST.value(),
				LocalDateTime.now()
		);

		return  new ResponseEntity<>(errorResponse.toString(), HttpStatus.BAD_REQUEST);
	}
}
