package com.demo.booksearch.search.book.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepository {

	Page<Book> search(String keyword, Pageable pageable);

}
