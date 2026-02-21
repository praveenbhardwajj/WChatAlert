package com.WChatAlert.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendOtp(String toEmail, String otp, String name) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("praveenbhardwajj02@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject("WChatAlert | Verify Your Email Address");

        String htmlContent =
                "<div style='font-family: Arial, sans-serif; padding: 20px; background-color: #f4f6f8;'>"
              + "  <div style='max-width: 500px; margin: auto; background: white; padding: 30px; border-radius: 10px;'>"
              + "    <h2 style='color: #2c3e50; text-align: center;'>WChatAlert</h2>"
              + "    <p style='font-size: 16px; color: #555;'>Hello <b>" + name + "</b>,</p>"
              + "    <p style='font-size: 16px; color: #555;'>Use the OTP below to verify your email address:</p>"
              + "    <div style='text-align: center; margin: 20px 0;'>"
              + "       <span style='font-size: 28px; font-weight: bold; letter-spacing: 5px; color: white; background: #4CAF50; padding: 10px 20px; border-radius: 8px;'>"
              + otp
              + "       </span>"
              + "    </div>"
              + "    <p style='font-size: 14px; color: #999;'>This OTP is valid for 5 minutes.</p>"
              + "    <hr>"
              + "    <p style='font-size: 12px; color: #bbb; text-align: center;'>If you did not request this, please ignore this email.</p>"
              + "  </div>"
              + "</div>";

        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
}
