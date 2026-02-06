package com.WChatAlert.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.WChatAlert.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	Optional<User> findByWhatsappNumber(String whatsappNumber);
}
