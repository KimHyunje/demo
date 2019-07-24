package com.demo.booksearch.search.book.infrastructure.http;

import static com.demo.booksearch.search.book.infrastructure.http.kakao.KakaoBookSearchResultFixture.kakaoBookSearchResult;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.client.RestClientException;

import com.demo.booksearch.search.book.domain.Book;
import com.demo.booksearch.search.book.infrastructure.http.BookRepositoryImpl;
import com.demo.booksearch.search.book.infrastructure.http.BookSearcher;
import com.demo.booksearch.search.book.infrastructure.http.SearchResult;

@RunWith(MockitoJUnitRunner.class)
public class BookRepositoryImplTest {

	private BookRepositoryImpl repository;

	@Mock
	private BookSearcher kakaoBookSearcher;

	@Mock
	private BookSearcher naverBookSearcher;

	private SearchResult searchResult = mock(SearchResult.class);
	private Page<Book> books = kakaoBookSearchResult().convert(PageRequest.of(1, 10));


	@Before
	public void setUp() {
		repository = new BookRepositoryImpl(kakaoBookSearcher, naverBookSearcher);

		given(searchResult.convert(any())).willReturn(books);
	}

	@Test
	public void testSearch_main이_정상() {
		given(kakaoBookSearcher.search(any(), any())).willReturn(searchResult);

		repository.search(any(), any());

		verify(kakaoBookSearcher).search(any(), any());
		verify(naverBookSearcher, never()).search(any(), any());
	}

	@Test
	public void testSearch_main이_예외고_sub가_정상() {
		given(kakaoBookSearcher.search(any(), any())).willThrow(RestClientException.class);
		given(naverBookSearcher.search(any(), any())).willReturn(searchResult);

		repository.search(any(), any());

		verify(kakaoBookSearcher).search(any(), any());
		verify(naverBookSearcher).search(any(), any());
	}

	@Test
	public void testSearch_main이_예외고_sub가_예외() {
		given(kakaoBookSearcher.search(any(), any())).willThrow(RestClientException.class);
		given(naverBookSearcher.search(any(), any())).willThrow(RestClientException.class);

		assertThatThrownBy(() -> repository.search(any(), any()));

		verify(kakaoBookSearcher).search(any(), any());
		verify(naverBookSearcher).search(any(), any());
	}

}
