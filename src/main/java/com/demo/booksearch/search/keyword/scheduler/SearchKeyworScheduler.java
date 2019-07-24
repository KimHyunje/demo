package com.demo.booksearch.search.keyword.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.demo.booksearch.search.keyword.application.SearchKeywordApplicationService;

@Component
public class SearchKeyworScheduler {

	private final SearchKeywordApplicationService applicationService;


	public SearchKeyworScheduler(SearchKeywordApplicationService searchKeyworApplicationService) {
		this.applicationService = searchKeyworApplicationService;
	}


	@Scheduled(fixedDelay = 200000)
	public void flushPerMinute() {
		applicationService.refresh();
	}

}
