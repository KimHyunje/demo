package com.demo.booksearch.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.demo.booksearch.user.application.UserApplicationService;

@Component
public class BookSearchUserDetailsService implements UserDetailsService {

	private UserApplicationService applicationService;


	public BookSearchUserDetailsService(UserApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		com.demo.booksearch.user.domain.User user = applicationService.search(username)
				.orElseThrow(() -> new UsernameNotFoundException("userId가 존재하지 않습니다."));
		
		return new User(
				user.getId(), 
				user.getPassword(), 
				List.of(new SimpleGrantedAuthority("USER")));
	}

}
