package com.demo.booksearch.search.history.interfaces.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.demo.booksearch.search.history.domain.SearchHistory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class SearchHistoriesResponse {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;


	private List<SearchHistoryElement> searchHistories = new ArrayList<>();


	public SearchHistoriesResponse(List<SearchHistory> searchHistories) {
		searchHistories.stream()
				.map(SearchHistoryElement::new)
				.forEach(element -> this.searchHistories.add(element));
	}


	@Getter
	@EqualsAndHashCode
	@ToString
	private class SearchHistoryElement {

		private String keyword;
		private String searchDateTime;


		SearchHistoryElement(SearchHistory searchHistory) {
			this.keyword = searchHistory.getKeyword();
			this.searchDateTime = searchHistory.getSearchDateTime().format(DATE_TIME_FORMATTER);
		}

	}

}
