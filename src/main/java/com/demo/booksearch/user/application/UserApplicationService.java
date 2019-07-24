package com.demo.booksearch.user.application;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.booksearch.user.domain.User;
import com.demo.booksearch.user.domain.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserApplicationService {

	private final PasswordEncoder passwordEncoder;

	private final UserRepository repository;


	public UserApplicationService(PasswordEncoder passwordEncoder, 
			UserRepository userRepository) {

		this.passwordEncoder = passwordEncoder;
		this.repository = userRepository;
	}


	@Transactional
	public User register(String id, String plainPassword) {
		String password = passwordEncoder.encode(plainPassword);
		var newUser = User.of(id, password);
		var managedUser = repository.save(newUser);
		log.info("managedUser: [{}]", managedUser);
		return managedUser;
	}

	@Transactional(readOnly = true)
	public Optional<User> search(String id) {
		return repository.findById(id);
	}

}
