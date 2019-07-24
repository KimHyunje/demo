package com.demo.booksearch.search.history.application;

import static com.demo.booksearch.search.history.domain.SearchHistoryFixture.searchHistory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.demo.booksearch.search.history.application.SearchHistoryApplicationService;
import com.demo.booksearch.search.history.domain.SearchHistory;
import com.demo.booksearch.search.history.domain.SearchHistoryRepository;

@RunWith(MockitoJUnitRunner.class)
public class SearchHistoryApplicationServiceTest {

	@InjectMocks
	private SearchHistoryApplicationService applicationService;

	@Mock
	private SearchHistoryRepository repository;

	private String userId = "userId";
	private String keyword = "키워드";
	private SearchHistory searchHistory1 = searchHistory();
	private SearchHistory searchHistory2 = searchHistory();
	private List<SearchHistory> searchHistories = List.of(searchHistory1, searchHistory2);


	@Test
	public void testRecord() {
		given(repository.save(any())).willReturn(searchHistory1);

		var searchHistory = applicationService.record(userId, keyword);

		assertThat(searchHistory).isEqualTo(searchHistory1);
	}

	@Test
	public void testSearch() {
		given(repository.findTop10ByUserIdOrderBySearchDateTimeDesc(userId))
				.willReturn(searchHistories);

		var actual = applicationService.search(userId);

		assertThat(actual).hasSameSizeAs(searchHistories)
				.contains(searchHistory1, searchHistory2);
	}

}
