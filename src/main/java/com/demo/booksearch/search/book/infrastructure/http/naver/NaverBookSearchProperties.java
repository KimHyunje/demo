package com.demo.booksearch.search.book.infrastructure.http.naver;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "book-search-server.naver")
public class NaverBookSearchProperties {

	private String url;
	private String clientId;
	private String clientSecret;

}
