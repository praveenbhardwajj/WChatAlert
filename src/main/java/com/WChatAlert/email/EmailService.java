package com.WChatAlert.email;


import jakarta.mail.MessagingException;

public interface EmailService {

    void sendOtp(String toEmail, String otp, String name) throws MessagingException;

}
