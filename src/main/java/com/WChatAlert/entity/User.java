package com.WChatAlert.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(unique = true)
	private String email;

	@Column(unique = true, nullable = false)
	private String whatsappNumber;
	
	@Column(nullable = false)
	private String password;

	private boolean verified = false;
	
	private String otp;
	
	private LocalDateTime otpExpiry;

	public User(Long id, String name, String email, String whatsappNumber, String password, boolean verified,
			String otp, LocalDateTime otpExpiry) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.whatsappNumber = whatsappNumber;
		this.password = password;
		this.verified = verified;
		this.otp = otp;
		this.otpExpiry = otpExpiry;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public LocalDateTime getOtpExpiry() {
		return otpExpiry;
	}

	public void setOtpExpiry(LocalDateTime otpExpiry) {
		this.otpExpiry = otpExpiry;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWhatsappNumber() {
		return whatsappNumber;
	}

	public void setWhatsappNumber(String whatsappNumber) {
		this.whatsappNumber = whatsappNumber;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public User(Long id, String name, String email, String password, String whatsappNumber, boolean verified) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.whatsappNumber = whatsappNumber;
		this.verified = verified;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", whatsappNumber=" + whatsappNumber
				+ ", verified=" + verified + "]";
	}

	// getters & setters
}
