package com.WChatAlert.service.impl;

import java.time.LocalDateTime;

import java.util.Random;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.WChatAlert.dto.LoginRequestDTO;
import com.WChatAlert.entity.User;
import com.WChatAlert.repository.UserRepository;
import com.WChatAlert.security.JwtUtil;
import com.WChatAlert.dto.LoginResponseDTO;
import com.WChatAlert.email.EmailService;
import com.WChatAlert.service.UserService;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	// password hash by security config class method password encoder
	private final PasswordEncoder passwordEncoder;
	// jwt utl
	private final JwtUtil jwtUtil;
	// pauthenticationmanage
	private final AuthenticationManager authenticationManager;

	private final EmailService emailService;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
			AuthenticationManager authenticationManager, EmailService emailService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
		this.emailService = emailService;
	}

	@Override
	public User createUser(User user) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		String otp = String.valueOf(new Random().nextInt(900000) + 100000);

		user.setOtp(otp);
		user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
		user.setVerified(false);

		User savedUser = userRepository.save(user);

		try {
			emailService.sendOtp(user.getEmail(), otp, user.getName());
		} catch (Exception e) {
			e.printStackTrace(); // Ye real error dikha dega
			throw new RuntimeException("Failed to send OTP email: " + e.getMessage());
		}

		return savedUser;
	}

	@Override
	public LoginResponseDTO login(LoginRequestDTO dto) {

		// Authenticate user
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmailOrNumber(), dto.getPassword()));

		// Get user from database
		User user = userRepository.findByEmail(authentication.getName())
				.orElseThrow(() -> new RuntimeException("User not found"));

		// 3Check if email is verified
		if (!user.isVerified()) {
			throw new RuntimeException("Email not verified. Please verify OTP first.");
		}

		// Generate JWT token
		String token = jwtUtil.genrateToken(authentication.getName());

		// 5Prepare response
		LoginResponseDTO response = new LoginResponseDTO();
		response.setToken(token);
		response.setSuccess(true);
		response.setMessage("Login Successful");
		response.setName(user.getName()); // real name from DB

		return response;
	}

	@Override
	public void verifyOtp(String email, String otp) {

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		if (user.getOtp() == null || !user.getOtp().equals(otp)) {
			throw new RuntimeException("Invalid OTP");
		}

		if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("OTP expired");
		}

		user.setVerified(true);
		user.setOtp(null);
		user.setOtpExpiry(null);

		userRepository.save(user);
	}

}
