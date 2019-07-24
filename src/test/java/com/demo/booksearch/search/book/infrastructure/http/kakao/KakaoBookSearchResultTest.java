package com.demo.booksearch.search.book.infrastructure.http.kakao;

import static com.demo.booksearch.search.book.infrastructure.http.kakao.KakaoBookSearchResultFixture.kakaoBookSearchResult;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.data.domain.PageRequest;

import com.demo.booksearch.search.book.infrastructure.http.kakao.KakaoBookSearchResult;

public class KakaoBookSearchResultTest {

	private KakaoBookSearchResult kakaoBookSearchResult = kakaoBookSearchResult();


	@Test
	public void testConvert() {
		var books = kakaoBookSearchResult.convert(PageRequest.of(1, 10));

		assertThat(books.getContent()).hasSameSizeAs(kakaoBookSearchResult.getDocuments());
		assertThat(books.getTotalElements()).isEqualTo(
				kakaoBookSearchResult.getMeta().getTotalCount() + kakaoBookSearchResult.getDocuments().size());
	}

}
