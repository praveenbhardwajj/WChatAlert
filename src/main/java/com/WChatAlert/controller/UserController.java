package com.WChatAlert.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.WChatAlert.dto.LoginRequestDTO;
import com.WChatAlert.dto.LoginResponseDTO;
import com.WChatAlert.entity.User;
import com.WChatAlert.service.UserService;


@RestController
@RequestMapping("/user/")
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/register")
	public User createUser(@RequestBody User user) {
		System.out.println("User Receved");
		return userService.createUser(user);
	}
	
	

}
