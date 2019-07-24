package com.demo.booksearch.search.history.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class SearchHistory {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;

	private String userId;

	@Getter
	private String keyword;

	@Getter
	private LocalDateTime searchDateTime;


	private SearchHistory(String userId, String keyword) {
		this.userId = userId;
		this.keyword = keyword;
		this.searchDateTime = LocalDateTime.now();
	}


	public static SearchHistory of(String userId, String keyword) {
		return new SearchHistory(userId, keyword);
	}

}
