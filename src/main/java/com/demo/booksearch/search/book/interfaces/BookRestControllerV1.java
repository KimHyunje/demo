package com.demo.booksearch.search.book.interfaces;

import static java.util.Objects.isNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.booksearch.search.book.application.BookApplicationService;
import com.demo.booksearch.search.book.domain.Book;
import com.demo.booksearch.search.book.interfaces.dto.BooksResponse;

@RestController
@RequestMapping("/v1/books")
public class BookRestControllerV1 {

	private final BookApplicationService applicationService;


	public BookRestControllerV1(BookApplicationService bookApplicationService) {
		this.applicationService = bookApplicationService;
	}


	@GetMapping
	public ResponseEntity<BooksResponse> search(
			@AuthenticationPrincipal UserDetails userDetails, 
			@RequestParam String keyword, 
			@RequestParam(required = false) Integer page, 
			@RequestParam(required = false) Integer size) {

		var userId = userDetails.getUsername();
		var pageable = defaultPageable(page, size);
		Page<Book> books = applicationService.search(userId, keyword, pageable);
		var assembler = new BookAssembler(keyword);
		BooksResponse responseBody = assembler.toResource(books);
		return ResponseEntity.ok(responseBody);
	}

	private Pageable defaultPageable(Integer page, Integer size) {
		if (isNull(page) || isNull(size)) return PageRequest.of(1, 10);
		
		return PageRequest.of(page, size);
	}

}
