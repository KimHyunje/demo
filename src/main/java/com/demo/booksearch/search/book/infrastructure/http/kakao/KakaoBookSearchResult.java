package com.demo.booksearch.search.book.infrastructure.http.kakao;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.demo.booksearch.search.book.domain.Book;
import com.demo.booksearch.search.book.infrastructure.http.SearchResult;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
@ToString
public class KakaoBookSearchResult implements SearchResult {

	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 10;

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

	private Meta meta;
	private List<Document> documents;


	@Override
	public Page<Book> convert(Pageable pageable) {
		var books = documents.stream()
				.map(this::toBook)
				.collect(toList());
		var total = meta.getTotalCount();
		var defaultPageable = isNull(pageable) ? PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE) : pageable;
		return new PageImpl<>(books, defaultPageable , total);
	}

	private Book toBook(Document document) {
		return Book.builder()
				.title(document.getTitle())
				.thumbnail(document.getThumbnail())
				.contents(document.getContents())
				.isbn(document.getIsbn())
				.authors(document.getAuthors())
				.publisher(document.getPublisher())
				.datetime(LocalDateTime.parse(document.getDatetime(), DATE_TIME_FORMATTER))
				.price(document.getPrice())
				.salePrice(document.getSalePrice())
				.build();
	}


	@Setter
	@NoArgsConstructor(access = AccessLevel.PACKAGE)
	@EqualsAndHashCode
	@ToString
	static class Meta {

		private Long total_count;
		private Integer pageable_count;
		private Boolean is_end;

		public Long getTotalCount() {
			return total_count;
		}
		
		public Integer getPageableCount() {
			return pageable_count;
		}
		
		public Boolean isEnd() {
			return is_end;
		}

	}


	@Getter
	@Setter
	@NoArgsConstructor(access = AccessLevel.PACKAGE)
	@EqualsAndHashCode
	@ToString
	static class Document {

		private String title;
		private String thumbnail;
		private String contents;
		private String isbn;
		private List<String> authors;
		private String publisher;
		private String datetime;
		private Integer price;
		private Integer salePrice;

	}

}
