package com.demo.booksearch.search.book.infrastructure.http.naver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.demo.booksearch.search.book.infrastructure.http.naver.NaverBookSearchProperties;
import com.demo.booksearch.search.book.infrastructure.http.naver.NaverBookSearchResult;
import com.demo.booksearch.search.book.infrastructure.http.naver.NaverBookSearcher;

@RunWith(MockitoJUnitRunner.class)public class NaverBookSearcherTest {

	private NaverBookSearcher searcher;

	@Mock
	private RestTemplateBuilder builder;

	@Mock
	private NaverBookSearchProperties properties;

	private RestTemplate restTemplate = mock(RestTemplate.class);

	@SuppressWarnings("unchecked")
	private ResponseEntity<NaverBookSearchResult> responseEntity = mock(ResponseEntity.class);

	private NaverBookSearchResult searchResult = NaverBookSearchResultFixture.naverBookSearchResult();


	@Before
	public void setUp() {
		given(builder.setConnectTimeout(any())).willReturn(builder);
		given(builder.setReadTimeout(any())).willReturn(builder);
		given(builder.build()).willReturn(restTemplate);
		given(properties.getUrl()).willReturn("https://abc.com/12345");
		given(properties.getClientId()).willReturn("1q2w3e4r");
		given(properties.getClientSecret()).willReturn("1q2w3e4r");
		searcher = new NaverBookSearcher(builder, properties);

		given(responseEntity.getBody()).willReturn(searchResult);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearch_정상() {
		given(restTemplate.exchange(any(RequestEntity.class), any(Class.class)))
				.willReturn(responseEntity);

		var kakaoBookSearchResult = searcher.search("제목", PageRequest.of(1, 10));

		assertThat(kakaoBookSearchResult).isEqualTo(searchResult);

		var requestEntityCaptor = ArgumentCaptor.forClass(RequestEntity.class);
		verify(restTemplate).exchange(requestEntityCaptor.capture(), any(Class.class));
		assertThat(requestEntityCaptor.getAllValues().get(0).getUrl().toString())
				.contains("query", "start", "display");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearch_예외() {
		given(restTemplate.exchange(any(RequestEntity.class), any(Class.class)))
				.willThrow(RestClientException.class);

		assertThatThrownBy(() -> searcher.search("제목", PageRequest.of(1, 10)))
				.isInstanceOf(RestClientException.class);
	}

}
