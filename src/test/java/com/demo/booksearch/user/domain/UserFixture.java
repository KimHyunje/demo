package com.demo.booksearch.user.domain;

import com.demo.booksearch.user.domain.User;

public class UserFixture {

	private UserFixture() {
	}


	public static User user() {
		return User.of("userId", "1234qwer");
	}

}
