package com.demo.booksearch.search.book.application;

import static com.demo.booksearch.search.book.infrastructure.http.kakao.KakaoBookSearchResultFixture.kakaoBookSearchResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.demo.booksearch.common.event.BookSearchedEvent;
import com.demo.booksearch.search.book.application.BookApplicationService;
import com.demo.booksearch.search.book.domain.Book;
import com.demo.booksearch.search.book.domain.BookRepository;

@RunWith(MockitoJUnitRunner.class)
public class BookApplicationServiceTest {

	@InjectMocks
	private BookApplicationService applicationService;

	@Mock
	private ApplicationEventPublisher eventPublisher;

	@Mock
	private BookRepository repository;

	private Page<Book> books = kakaoBookSearchResult().convert(PageRequest.of(1, 10));


	@Test
	public void testSearch() {
		given(repository.search(any(), any())).willReturn(books);

		var books = applicationService.search(any(), any(), PageRequest.of(1, 10));

		assertThat(books).isEqualTo(this.books);
		verify(eventPublisher).publishEvent(any(BookSearchedEvent.class));
	}

}
