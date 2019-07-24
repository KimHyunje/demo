package com.demo.booksearch.search.keyword.adapter;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.demo.booksearch.common.event.BookSearchedEvent;
import com.demo.booksearch.search.keyword.application.SearchKeywordApplicationService;

@Component
public class SearchKeywordEventListener {

	private final SearchKeywordApplicationService applicationService;


	public SearchKeywordEventListener(SearchKeywordApplicationService searchKeywordApplicationService) {
		this.applicationService = searchKeywordApplicationService;
	}


	@Async
	@EventListener
	public void listen(BookSearchedEvent event) {
		var keyword = event.getKeyword();
		applicationService.increaseSearchCount(keyword);
	}

}
