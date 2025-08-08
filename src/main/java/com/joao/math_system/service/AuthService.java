package com.joao.math_system.service;

import com.joao.math_system.dto.AuthDto.*;
import com.joao.math_system.entities.Role;
import com.joao.math_system.entities.User;
import com.joao.math_system.repository.RoleRepository;
import com.joao.math_system.repository.UserRepository;
import com.joao.math_system.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService  jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthResponseDTO register(UserRegisterDTO request) {
		if (userRepository.findByEmail(request.email()).isPresent()) {
			throw new IllegalArgumentException("Email already in use");
		}

		Role userRole = roleRepository.findByName("ROLE_USER")
				.orElseThrow(() -> new IllegalArgumentException("Role Not Found"));

		var user = new User();
		user.setName(request.name());
		user.setEmail(request.email());
		user.setPassword(passwordEncoder.encode(request.password()));
		user.setRoles(Set.of(userRole));

		userRepository.save(user);

		var accessToken = jwtService.generateAccessToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);

		return new AuthResponseDTO(accessToken, refreshToken);
	}

	public AuthResponseDTO login(LoginRequestDTO request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.email(), request.password())
		                                  );

		var user = userRepository.findByEmail(request.email())
		                         .orElseThrow(() -> new UsernameNotFoundException("User not found"));

		var accessToken = jwtService.generateAccessToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);

		return new AuthResponseDTO(accessToken, refreshToken);
	}

	public AuthResponseDTO refreshToken(RefreshTokenRequestDTO request) {
		String userEmail = jwtService.extractUsername(request.refreshToken());
		var user = userRepository.findByEmail(userEmail)
		                         .orElseThrow(() -> new UsernameNotFoundException("User not found"));

		if (jwtService.isTokenValid(request.refreshToken(), user)) {
			var accessToken = jwtService.generateAccessToken(user);
			return new AuthResponseDTO(accessToken, request.refreshToken());
		}

		throw new IllegalArgumentException("Invalid refresh token");
	}
}
