package com.demo.booksearch.common.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class BookSearchedEvent {

	private final String userId;
	private final String keyword;


	public BookSearchedEvent(String userId, String keyword) {
		this.userId = userId;
		this.keyword = keyword;
	}

}
