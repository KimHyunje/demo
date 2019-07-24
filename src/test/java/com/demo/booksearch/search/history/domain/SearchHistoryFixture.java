package com.demo.booksearch.search.history.domain;

import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import com.demo.booksearch.search.history.domain.SearchHistory;

public class SearchHistoryFixture {

	private SearchHistoryFixture() {
	}


	public static SearchHistory searchHistory() {
		var searchHistory = SearchHistory.of("userId", "키워드");
		setField(searchHistory, "id", nextLong());
		return searchHistory;
	}

}
