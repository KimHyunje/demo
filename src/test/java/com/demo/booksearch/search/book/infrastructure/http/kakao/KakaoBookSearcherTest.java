package com.demo.booksearch.search.book.infrastructure.http.kakao;

import static com.demo.booksearch.search.book.infrastructure.http.kakao.KakaoBookSearchResultFixture.kakaoBookSearchResult;
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

import com.demo.booksearch.search.book.infrastructure.http.kakao.KakaoBookSearchProperties;
import com.demo.booksearch.search.book.infrastructure.http.kakao.KakaoBookSearchResult;
import com.demo.booksearch.search.book.infrastructure.http.kakao.KakaoBookSearcher;

@RunWith(MockitoJUnitRunner.class)
public class KakaoBookSearcherTest {

	private KakaoBookSearcher searcher;

	@Mock
	private RestTemplateBuilder builder;

	@Mock
	private KakaoBookSearchProperties properties;

	private RestTemplate restTemplate = mock(RestTemplate.class);

	@SuppressWarnings("unchecked")
	private ResponseEntity<KakaoBookSearchResult> responseEntity = mock(ResponseEntity.class);

	private KakaoBookSearchResult searchResult = kakaoBookSearchResult();


	@Before
	public void setUp() {
		given(builder.setConnectTimeout(any())).willReturn(builder);
		given(builder.setReadTimeout(any())).willReturn(builder);
		given(builder.build()).willReturn(restTemplate);
		given(properties.getUrl()).willReturn("https://abc.com/12345");
		given(properties.getAppKey()).willReturn("1q2w3e4r");
		searcher = new KakaoBookSearcher(builder, properties);

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
				.contains("query", "page", "size");
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
