package com.demo.booksearch.search.book.application;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.booksearch.common.event.BookSearchedEvent;
import com.demo.booksearch.search.book.domain.Book;
import com.demo.booksearch.search.book.domain.BookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookApplicationService {

	private final ApplicationEventPublisher eventPublisher;

	private final BookRepository repository;


	public BookApplicationService(ApplicationEventPublisher applicationEventPublisher, 
			BookRepository bookRepository) {

		this.eventPublisher = applicationEventPublisher;
		this.repository = bookRepository;
	}


	public Page<Book> search(String userId, String keyword, Pageable pageable) {
		var books = repository.search(keyword, pageable);
		log.info("books: [{}]", books);
		eventPublisher.publishEvent(new BookSearchedEvent(userId, keyword));
		return books;
	}

}
