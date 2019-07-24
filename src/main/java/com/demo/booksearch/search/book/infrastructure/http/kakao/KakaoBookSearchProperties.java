package com.demo.booksearch.search.book.infrastructure.http.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "book-search-server.kakao")
public class KakaoBookSearchProperties {

	private String url;
	private String appKey;

}
