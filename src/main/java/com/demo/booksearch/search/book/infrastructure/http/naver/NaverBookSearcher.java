package com.demo.booksearch.search.book.infrastructure.http.naver;

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
public class NaverBookSearcher implements BookSearcher {

	private final RestTemplate restTemplate;

	private final String url;
	private final String clientId;
	private final String clientSecret;


	public NaverBookSearcher(RestTemplateBuilder builder, 
			NaverBookSearchProperties naverProperties) {
		this.restTemplate = builder
				.setConnectTimeout(ofSeconds(1))
				.setReadTimeout(ofSeconds(3))
				.build();
		this.url = naverProperties.getUrl();
		this.clientId = naverProperties.getClientId();
		this.clientSecret = naverProperties.getClientSecret();
	}


	@Override
	public NaverBookSearchResult search(String keyword, Pageable pageable) {
		if (isNull(pageable)) throw new InternalServerErrorException();

		var uriComponentBuilder = fromUriString(url)
				.queryParam("query", keyword)
				// Pageable의 Offset은 0부터, 네이버의 start는 1부터 시작
				.queryParam("start", pageable.getOffset() + 1)
				.queryParam("display", pageable.getPageSize());

		// 네이버는 한글을 지원하지 않아서 인코딩 해줘야 함
		var uri = uriComponentBuilder.encode().build().toUri();
		var requestEntity = get(uri)
				.header("X-Naver-Client-Id", clientId)
				.header("X-Naver-Client-Secret", clientSecret)
				.build();
		return restTemplate
				.exchange(requestEntity, NaverBookSearchResult.class)
				.getBody();
	}

}
