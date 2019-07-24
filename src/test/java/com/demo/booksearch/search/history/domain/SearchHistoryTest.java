package com.demo.booksearch.search.history.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.getField;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.demo.booksearch.search.history.domain.SearchHistory;

public class SearchHistoryTest {

	private String userId = "userId";
	private String keyword = "키워트";


	@Test
	public void testOf() {
		var searchHistory = SearchHistory.of(userId, keyword);

		assertThat(getField(searchHistory, "userId")).isEqualTo(userId);
		assertThat(searchHistory.getKeyword()).isEqualTo(keyword);
		assertThat(searchHistory.getSearchDateTime()).isCloseTo(
				LocalDateTime.now(), Assertions.within(1, ChronoUnit.SECONDS));
	}

}
