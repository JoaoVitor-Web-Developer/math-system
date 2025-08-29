package com.joao.math_system.controller;

import com.joao.math_system.dto.UserDTO.*;
import com.joao.math_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PatchMapping("/me")
	public ResponseEntity<Void> updateCurrentUser(@RequestBody @Valid UserUpdateDTO user) {
		userService.updateCurrentUser(user);
		return ResponseEntity.ok().build();
	}
}
