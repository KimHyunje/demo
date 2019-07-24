package com.demo.booksearch.search.book.infrastructure.http;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.demo.booksearch.search.book.domain.Book;

public interface SearchResult {

	Page<Book> convert(Pageable pageable);

}
