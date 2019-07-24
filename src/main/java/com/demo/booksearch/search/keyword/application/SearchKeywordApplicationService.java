package com.demo.booksearch.search.keyword.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.booksearch.search.keyword.domain.SearchKeyword;
import com.demo.booksearch.search.keyword.domain.SearchKeywordRepository;

@Service
public class SearchKeywordApplicationService {

	private final SearchKeywordRepository repository;


	public SearchKeywordApplicationService(SearchKeywordRepository searchKeywordRepository) {
		this.repository = searchKeywordRepository;
	}


	public void increaseSearchCount(String searchedKeyword) {
		repository.increaseSearchCount(searchedKeyword);
	}

	public List<SearchKeyword> searchTop(int rank) {
		return repository.findTop(rank);
	}

	public void refresh() {
		repository.refresh();
	}

}
