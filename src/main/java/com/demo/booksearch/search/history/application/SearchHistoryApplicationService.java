package com.demo.booksearch.search.history.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.demo.booksearch.search.history.domain.SearchHistory;
import com.demo.booksearch.search.history.domain.SearchHistoryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SearchHistoryApplicationService {

	private final SearchHistoryRepository repository;


	public SearchHistoryApplicationService(SearchHistoryRepository searchHistoryRepository) {
		this.repository = searchHistoryRepository;
	}


	@Transactional
	public SearchHistory record(String userId, String keyword) {
		var newSearchHistory = SearchHistory.of(userId, keyword);
		var managedSearchHistory = repository.save(newSearchHistory);
		log.info("managedSearchHistory: [{}]", managedSearchHistory);
		return managedSearchHistory;
	}

	@Transactional(readOnly = true)
	public List<SearchHistory> search(String userId) {
		return repository.findTop10ByUserIdOrderBySearchDateTimeDesc(userId);
	}

}
