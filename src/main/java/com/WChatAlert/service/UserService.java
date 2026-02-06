package com.WChatAlert.service;

import com.WChatAlert.dto.LoginRequestDTO;
import com.WChatAlert.dto.LoginResponseDTO;
import com.WChatAlert.entity.User;

public interface UserService {
	User createUser(User user);

	LoginResponseDTO login(LoginRequestDTO dto);
}
