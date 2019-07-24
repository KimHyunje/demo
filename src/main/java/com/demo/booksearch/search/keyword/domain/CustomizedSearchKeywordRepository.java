package com.demo.booksearch.search.keyword.domain;

import java.util.List;

public interface CustomizedSearchKeywordRepository {

	void increaseSearchCount(String searchedKeyword);

	List<SearchKeyword> findTop(int rank);

	void refresh();

}
