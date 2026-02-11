package com.WChatAlert.service.impl;

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

import com.WChatAlert.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	// password hash by security config class method password encoder
	private final PasswordEncoder passwordEncoder;
	// jwt utl
	private final JwtUtil jwtUtil;
	// pauthenticationmanage
	private final AuthenticationManager authenticationManager;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
			AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public User createUser(User user) {

		// Set hashing for password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		// New user always unverified
		user.setVerified(true);
		return userRepository.save(user);
	}

//	@Override
//	public LoginResponseDTO login(LoginRequestDTO dto) {
//		// TODO Auto-generated method stub
//		User user;
//
//		// login by email or number // filer for email
//		if (dto.getEmailOrNumber().contains("@")) {
//			user = userRepository.findByEmail(dto.getEmailOrNumber())
//					.orElseThrow(() -> new RuntimeException("User not Found With This Email"));
//		} else {
//			user = userRepository.findByWhatsappNumber(dto.getEmailOrNumber())
//					.orElseThrow(() -> new RuntimeException("No user found wit this Phone number"));
//		}
//		// check password using encode.match
//		if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
//			throw new RuntimeException("Invlid Password");
//		}
//
//		if (!user.isVerified()) {
//			throw new RuntimeException("User not verified");
//		}
//		return new LoginResponseDTO("Login successful", true);
//	}
	@Override
	public LoginResponseDTO login(LoginRequestDTO dto) {
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmailOrNumber(), dto.getPassword()));
		
		String token =jwtUtil.genrateToken(authentication.getName());
		
		LoginResponseDTO response = new LoginResponseDTO();
		response.setToken(token);
		response.setSuccess(true); 
		response.setMessage("Login Succesfull");
		return response;
	}
	
}
