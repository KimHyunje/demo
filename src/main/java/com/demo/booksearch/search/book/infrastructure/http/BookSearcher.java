package com.demo.booksearch.search.book.infrastructure.http;

import org.springframework.data.domain.Pageable;

public interface BookSearcher {

	SearchResult search(String keyword, Pageable pageable);

}
