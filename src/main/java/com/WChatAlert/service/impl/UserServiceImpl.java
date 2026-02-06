package com.WChatAlert.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.WChatAlert.dto.LoginRequestDTO;
import com.WChatAlert.entity.User;
import com.WChatAlert.repository.UserRepository;
import com.WChatAlert.dto.LoginRequestDTO;
import com.WChatAlert.dto.LoginResponseDTO;

import com.WChatAlert.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	// password hash by security config class method password encoder
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User createUser(User user) {

		// Set hashing for password
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return userRepository.save(user);
	}

	@Override
	public LoginResponseDTO login(LoginRequestDTO dto) {
		// TODO Auto-generated method stub
		User user;

		// login by email or number // filer for email
		if (dto.getEmailOrNumber().contains("@")) {
			user = userRepository.findByEmail(dto.getEmailOrNumber())
					.orElseThrow(() -> new RuntimeException("User not Found With This Email"));
		} else {
			user = userRepository.findByWhatsappNumber(dto.getEmailOrNumber())
					.orElseThrow(() -> new RuntimeException("No user found wit this Phone number"));
		}
		// check password using encode.match
		if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invlid Password");
		}

		if (!user.isVerified()) {
			throw new RuntimeException("User not verified");
		}
		return new LoginResponseDTO("Login successful", true);
	}
}
