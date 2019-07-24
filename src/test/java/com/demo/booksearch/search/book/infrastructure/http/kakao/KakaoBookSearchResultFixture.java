package com.demo.booksearch.search.book.infrastructure.http.kakao;

import java.util.List;

import com.demo.booksearch.search.book.infrastructure.http.kakao.KakaoBookSearchResult;
import com.demo.booksearch.search.book.infrastructure.http.kakao.KakaoBookSearchResult.Document;
import com.demo.booksearch.search.book.infrastructure.http.kakao.KakaoBookSearchResult.Meta;

public class KakaoBookSearchResultFixture {

	private KakaoBookSearchResultFixture() {
	}


	public static KakaoBookSearchResult kakaoBookSearchResult() {
		var searchResult = new KakaoBookSearchResult();
		searchResult.setMeta(meta());
		searchResult.setDocuments(documents());
		return searchResult;
	}

	private static Meta meta() {
		var meta = new Meta();
		meta.setTotal_count(10L);
		meta.setPageable_count(9);
		meta.setIs_end(false);
		return meta;
	}

	private static List<Document> documents() {
		Document doc1 = new Document();
		doc1.setTitle("제목1");
		doc1.setThumbnail("https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1467038");
		doc1.setContents("컨텐츠1");
		doc1.setIsbn("8996991341 9788996991342");
		doc1.setAuthors(List.of("저자A1", "저자B1"));
		doc1.setPublisher("출판사1");
		doc1.setDatetime("2019-01-01T00:00:00.000+09:00");
		doc1.setPrice(10000);
		doc1.setSalePrice(9000);

		Document doc2 = new Document();
		doc2.setTitle("제목2");
		doc2.setThumbnail("https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1467038");
		doc2.setContents("컨텐츠2");
		doc2.setIsbn("8996991341 9788996991343");
		doc2.setAuthors(List.of("저자A2", "저자B2"));
		doc2.setPublisher("출판사2");
		doc2.setDatetime("2019-02-02T00:00:00.000+09:00");
		doc2.setPrice(20000);
		doc2.setSalePrice(18000);

		return List.of(doc1, doc2);
	}

}
