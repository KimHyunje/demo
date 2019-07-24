package com.demo.booksearch.search.history.adapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.demo.booksearch.common.event.BookSearchedEvent;
import com.demo.booksearch.search.history.adapter.SearchHistoryEventListener;
import com.demo.booksearch.search.history.application.SearchHistoryApplicationService;

@RunWith(MockitoJUnitRunner.class)
public class SearchHistoryEventListenerTest {

	@InjectMocks
	private SearchHistoryEventListener eventListener;

	@Mock
	private SearchHistoryApplicationService applicationService;

	private BookSearchedEvent event = mock(BookSearchedEvent.class);


	@Before
	public void setUp() {
		given(event.getUserId()).willReturn("userId");
		given(event.getKeyword()).willReturn("키워드");
	}

	@Test
	public void testListen() {
		eventListener.listen(event);

		verify(applicationService).record(any(), any());
	}

}
