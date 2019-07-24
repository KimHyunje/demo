package com.demo.booksearch.user.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(of = "id")
@ToString
@Entity
public class User {

	@Getter
	@Id
	private String id;

	@Getter
	private String password;


	private User(String id, String password) {
		this.id = id;
		this.password = password;
	}


	public static User of(String id, String password) {
		return new User(id, password);
	}

}
