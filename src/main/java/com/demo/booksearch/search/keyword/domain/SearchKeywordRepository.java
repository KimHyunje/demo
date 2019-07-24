package com.demo.booksearch.search.keyword.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchKeywordRepository extends 
		JpaRepository<SearchKeyword, String>, CustomizedSearchKeywordRepository {

}
