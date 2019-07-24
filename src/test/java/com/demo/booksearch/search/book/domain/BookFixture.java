package com.demo.booksearch.search.book.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.demo.booksearch.search.book.domain.Book;

public class BookFixture {

	private BookFixture() {
	}


	public static Book book() {
		return Book.builder()
				.title("제목1")
				.thumbnail("https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1467038")
				.contents("컨텐츠1")
				.isbn("8996991341 9788996991342")
				.authors(List.of("저자A1", "저자B1"))
				.publisher("출판사1")
				.datetime(LocalDateTime.now())
				.price(10000)
				.salePrice(9000)
				.build();
	}

}
