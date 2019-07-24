package com.demo.booksearch.search.book.infrastructure.http;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.demo.booksearch.common.exception.InternalServerErrorException;
import com.demo.booksearch.search.book.domain.Book;
import com.demo.booksearch.search.book.domain.BookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BookRepositoryImpl implements BookRepository {

	private final BookSearcher mainBookSearcher;
	private final BookSearcher subBookSearcher;


	public BookRepositoryImpl(BookSearcher kakaoBookSearcher, BookSearcher naverBookSearcher) {
		this.mainBookSearcher = kakaoBookSearcher;
		this.subBookSearcher = naverBookSearcher;
	}


	@Override
	public Page<Book> search(String keyword, Pageable pageable) {
		SearchResult searchResult;
		try {
			searchResult = mainBookSearcher.search(keyword, pageable);
			log.info("searchResult: [{}]", searchResult);
		} catch (RestClientException e) {
			log.error("{}", e);
			try {
				searchResult = subBookSearcher.search(keyword, pageable);
				log.info("searchResult: [{}]", searchResult);
			} catch (Exception e2) {
				log.error("{}", e);
				throw new InternalServerErrorException();
			}
		}
		return searchResult.convert(pageable);
	}

}
