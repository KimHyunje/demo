package com.demo.booksearch.search.keyword.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.booksearch.search.keyword.application.SearchKeywordApplicationService;
import com.demo.booksearch.search.keyword.interfaces.dto.SearchKeyworsResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/search-keywords")
public class SearchKeywordRestControllerV1 {

	private final SearchKeywordApplicationService applicationService;


	public SearchKeywordRestControllerV1(SearchKeywordApplicationService searchKeyworApplicationService) {
		this.applicationService = searchKeyworApplicationService;
	}


	@GetMapping
	public ResponseEntity<SearchKeyworsResponse> search(@RequestParam int rank) {
		log.info("rank: [{}]", rank);
		var searchKeywords = applicationService.searchTop(rank);
		var searchKeywordsResponse = new SearchKeyworsResponse(searchKeywords);
		var responseEntity = ResponseEntity.ok(searchKeywordsResponse);
		log.info("responseEntity: [{}]", responseEntity); 
		return responseEntity;
	}

}
