package com.demo.booksearch.search.history.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long>{

	List<SearchHistory> findTop10ByUserIdOrderBySearchDateTimeDesc(String userId);

}
