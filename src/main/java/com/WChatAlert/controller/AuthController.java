package com.WChatAlert.controller;

import org.springframework.web.bind.annotation.*;
import com.WChatAlert.dto.LoginRequestDTO;
import com.WChatAlert.dto.LoginResponseDTO;
import com.WChatAlert.dto.VerifyOtpDTO;
import com.WChatAlert.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
     public AuthController(UserService userService) {
    	 this.userService=userService;
     }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO dto) {
        return userService.login(dto);
    }
    
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody VerifyOtpDTO dto) {
        userService.verifyOtp(dto.getEmail(), dto.getOtp());
        return "Email Verified Successfully";
    }

}
