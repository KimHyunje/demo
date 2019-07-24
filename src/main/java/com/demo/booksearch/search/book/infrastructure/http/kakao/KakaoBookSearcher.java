package com.demo.booksearch.search.book.infrastructure.http.kakao;

import static java.time.Duration.ofSeconds;
import static java.util.Objects.isNull;
import static org.springframework.http.RequestEntity.get;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.demo.booksearch.common.exception.InternalServerErrorException;
import com.demo.booksearch.search.book.infrastructure.http.BookSearcher;

@Component
public class KakaoBookSearcher implements BookSearcher {

	private final RestTemplate restTemplate;

	private final String url;
	private final String appKey;


	public KakaoBookSearcher(RestTemplateBuilder builder, 
			KakaoBookSearchProperties kakaoProperties) {

		this.restTemplate = builder
				.setConnectTimeout(ofSeconds(1))
				.setReadTimeout(ofSeconds(3))
				.build();
		this.url = kakaoProperties.getUrl();
		this.appKey = kakaoProperties.getAppKey();
	}


	@Override
	public KakaoBookSearchResult search(String keyword, Pageable pageable) {
		if (isNull(pageable)) throw new InternalServerErrorException();

		var uriComponentBuilder = fromUriString(url)
				.queryParam("query", keyword)
				// Pageable의 pageNumber는 0부터, 카카오의 page는 1부터 시작
				.queryParam("page", pageable.getPageNumber() + 1)
				.queryParam("size", pageable.getPageSize());

		var uri = uriComponentBuilder.build().toUri();
		var requestEntity = get(uri)
				.header("Authorization", "KakaoAK " + appKey)
				.build();
		return restTemplate
				.exchange(requestEntity, KakaoBookSearchResult.class)
				.getBody();
	}

}
