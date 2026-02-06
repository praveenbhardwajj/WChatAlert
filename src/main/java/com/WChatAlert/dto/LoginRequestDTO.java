package com.WChatAlert.dto;


public class LoginRequestDTO {
	private String emailOrNumber;
	private String password;

	public String getEmailOrNumber() {
		return emailOrNumber;
	}

	public void setEmailOrNumber(String emailOrNumber) {
		this.emailOrNumber = emailOrNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
