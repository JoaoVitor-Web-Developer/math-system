package com.joao.math_system.service;

import com.joao.math_system.dto.UserDTO.*;
import com.joao.math_system.entities.User;
import com.joao.math_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository  userRepository;

	@Transactional
	public void updateCurrentUser(UserUpdateDTO userDto) {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDto.name() != null && !userDto.name().isEmpty()) {
			currentUser.setName(userDto.name());
		}
		if (userDto.password() != null && !userDto.password().isEmpty()) {
			currentUser.setPassword(userDto.password());
		}
		if (userDto.pricePerSquareMeter() != null) {
			currentUser.setPricePerSquareMeter(userDto.pricePerSquareMeter());
		}

		userRepository.save(currentUser);
	}
}
