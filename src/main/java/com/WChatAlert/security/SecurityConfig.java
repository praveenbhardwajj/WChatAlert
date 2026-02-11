package com.WChatAlert.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
 * SecurityConfig
 * ----------------
 * Ye class Spring Security ka main configuration center hai.
 * Yahi decide hota hai:
 *  - Kaunse endpoints public hain
 *  - Kaunse endpoints protected hain
 *  - Session stateful ya stateless hogi
 *  - JWT filter kaha add hoga
 */
@Configuration
public class SecurityConfig {

	// Custom JWT filter inject ho raha hai
	// Ye har request me token check karega
	private final JwtFilter jwtFilter;

	public SecurityConfig(JwtFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}

	/*
	 * SecurityFilterChain Bean ------------------------- Ye Spring Security ko
	 * batata hai: - CSRF enable/disable - Session policy kya hogi - Kaunse URLs
	 * public hain - JWT filter kab execute hoga
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http

				// CSRF disable kiya kyunki hum REST API + JWT use kar rahe hain
				// CSRF mostly form-based login me use hota hai
				.csrf(csrf -> csrf.disable())

				// Stateless session ka matlab:
				// Server koi session store nahi karega
				// Har request me JWT token se authentication hoga
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// Authorization rules define ho rahe hain
				.authorizeHttpRequests(auth -> auth

						// /auth/** endpoints public rahenge
						// (login, register ke liye token nahi chahiye)
						.requestMatchers("/auth/login").permitAll()
						.requestMatchers("/user/register").permitAll()
						// Baaki sab endpoints ke liye authentication required
						.anyRequest().authenticated())

				// JWT filter ko Spring ke default username-password filter se pehle run kar
				// rahe hain
				// Taaki token validate ho jaye request process hone se pehle
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	
	
	
	/*
	 * PasswordEncoder Bean --------------------- BCrypt password hashing ke liye
	 * use hota hai Registration ke time password hash hoga Login ke time raw
	 * password vs hashed password compare hoga
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	

	// Ye bean Spring Security ka AuthenticationManager expose karta hai
	// AuthenticationManager login process ko handle karta hai
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

		// AuthenticationConfiguration ek Spring-provided class hai
		// Isme already configured hota hai:
		// - UserDetailsService
		// - PasswordEncoder
		// - AuthenticationProvider

		// Yaha hum Spring ka internally configured AuthenticationManager le rahe hain
		// Taaki hum ise AuthController me inject karke use kar sakein

		return config.getAuthenticationManager();
	}

}
