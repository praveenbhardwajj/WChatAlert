package com.WChatAlert.dto;


public class LoginRequestDTO {
	private String emailOrNumber;
	private String password;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

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

	public LoginRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


}
