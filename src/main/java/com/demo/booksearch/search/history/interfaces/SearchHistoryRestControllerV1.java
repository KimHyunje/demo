package com.demo.booksearch.search.history.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.booksearch.common.exception.BadRequestException;
import com.demo.booksearch.search.history.application.SearchHistoryApplicationService;
import com.demo.booksearch.search.history.interfaces.dto.SearchHistoriesResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/users/{userId}/search-histories")
public class SearchHistoryRestControllerV1 {

	private final SearchHistoryApplicationService applicationService;

	
	public SearchHistoryRestControllerV1(
			SearchHistoryApplicationService searchHistoryApplicationService) {

		this.applicationService = searchHistoryApplicationService;
	}


	@GetMapping
	public ResponseEntity<SearchHistoriesResponse> searchHistory(
			@AuthenticationPrincipal UserDetails userDetails, 
			@PathVariable String userId) {

		log.info("userDetails: [{}], userId: [{}]", userDetails, userId);
		if (!userDetails.getUsername().equals(userId)) throw new BadRequestException();

		var searchHistories = applicationService.search(userId);
		var searchHistoriesResponse = new SearchHistoriesResponse(searchHistories);
		var responseEntity = ResponseEntity.ok(searchHistoriesResponse);
		log.info("responseEntity: [{}]", responseEntity);
		return responseEntity;
	}

}
