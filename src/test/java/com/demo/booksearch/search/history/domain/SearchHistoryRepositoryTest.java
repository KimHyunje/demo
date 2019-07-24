package com.demo.booksearch.search.history.domain;

import static java.util.stream.Stream.iterate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.booksearch.search.keyword.infrastructure.SearchKeywordRepositoryImpl;

@RunWith(SpringRunner.class)
@DataJpaTest(
		includeFilters = @Filter(
				type = FilterType.ASSIGNABLE_TYPE, 
				classes = SearchKeywordRepositoryImpl.class))
public class SearchHistoryRepositoryTest {

	@Autowired
	private SearchHistoryRepository searchHistoryRepository;
	
	@Autowired
	private TestEntityManager entityManager;

	private String userId1 = "userId1";
	private String userId2 = "userId2";


	@Before
	public void setUp() {
		iterate(1, i -> i + 1)
				.limit(11)
				.forEach(i -> {
					SearchHistory searchHistory1 = SearchHistory.of(userId1, String.valueOf(i));
					setField(searchHistory1, "searchDateTime", LocalDateTime.now().withDayOfYear(i));
					entityManager.persistAndFlush(searchHistory1);

					SearchHistory searchHistory2 = SearchHistory.of(userId2, String.valueOf(i));
					setField(searchHistory2, "searchDateTime", LocalDateTime.now().withDayOfYear(i));
					entityManager.persistAndFlush(searchHistory2);
				});
	}

	@Test
	public void testFindTop10ByUserIdOrderBySearchDateTimeDesc() {
		var searchHistories = 
				searchHistoryRepository.findTop10ByUserIdOrderBySearchDateTimeDesc(userId1);

		assertThat(searchHistories).hasSize(10);
		searchHistories.forEach(searchHistory -> 
				assertThat(searchHistory.getSearchDateTime().getDayOfYear()).isNotEqualTo(1));
	}

}
