package com.demo.booksearch.search.history.adapter;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.demo.booksearch.common.event.BookSearchedEvent;
import com.demo.booksearch.search.history.application.SearchHistoryApplicationService;

@Component
public class SearchHistoryEventListener {

	private final SearchHistoryApplicationService applicationService;


	public SearchHistoryEventListener(
			SearchHistoryApplicationService searchHistoryApplicationService) {

		this.applicationService = searchHistoryApplicationService;
	}


	@Async
	@EventListener
	public void listen(BookSearchedEvent event) {
		var userId = event.getUserId();
		var keyword = event.getKeyword();
		applicationService.record(userId, keyword);
	}

}
