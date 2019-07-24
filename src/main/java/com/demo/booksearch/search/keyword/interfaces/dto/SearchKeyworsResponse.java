package com.demo.booksearch.search.keyword.interfaces.dto;

import java.util.ArrayList;
import java.util.List;

import com.demo.booksearch.search.keyword.domain.SearchKeyword;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class SearchKeyworsResponse {

	private List<SearchKeyworElement> searchKeywords = new ArrayList<>();


	public SearchKeyworsResponse(List<SearchKeyword> searchKeywors) {
		searchKeywors.stream()
				.map(SearchKeyworElement::new)
				.forEach(element -> this.searchKeywords.add(element));
	}


	@Getter
	@EqualsAndHashCode
	@ToString
	private class SearchKeyworElement {

		private String keyword;
		private long searchCount;

		SearchKeyworElement(SearchKeyword searchKeywor) {
			this.keyword = searchKeywor.getKeyword();
			this.searchCount = searchKeywor.getSearchCount();
		}

	}

}
