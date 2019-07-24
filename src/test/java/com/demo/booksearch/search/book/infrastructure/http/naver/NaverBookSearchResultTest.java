package com.demo.booksearch.search.book.infrastructure.http.naver;

import static com.demo.booksearch.search.book.infrastructure.http.naver.NaverBookSearchResultFixture.naverBookSearchResult;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.data.domain.PageRequest;

import com.demo.booksearch.search.book.infrastructure.http.naver.NaverBookSearchResult;

public class NaverBookSearchResultTest {

	private NaverBookSearchResult naverBookSearchResult = naverBookSearchResult();


	@Test
	public void testConvert() {
		var books = naverBookSearchResult.convert(PageRequest.of(1, 10));

		assertThat(books.getContent()).hasSameSizeAs(naverBookSearchResult.getItems());
		assertThat(books.getTotalElements()).isEqualTo(naverBookSearchResult.getTotal());
	}

}
