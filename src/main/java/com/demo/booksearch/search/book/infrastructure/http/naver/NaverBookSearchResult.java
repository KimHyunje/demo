package com.demo.booksearch.search.book.infrastructure.http.naver;

import static java.util.Objects.isNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
public class NaverBookSearchResult implements SearchResult {

	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 10;

    private String lastBuildDate;
    private Long total;
    private Integer start;
    private Integer display;
    private List<Item> items;


	@Override
	public Page<Book> convert(Pageable pageable) {
		var books = items.stream()
				.map(this::toBook)
				.collect(Collectors.toList());
		var defaultPageable = isNull(pageable) ? PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE) : pageable;
		return new PageImpl<>(books, defaultPageable , total);
	}

	private Book toBook(Item item) {
		return Book.builder()
				.title(item.getTitle())
				.thumbnail(item.getImage())
				.contents(item.getDescription())
				.isbn(item.getIsbn())
				.authors(List.of(item.getAuthor()))
				.publisher(item.getPublisher())
				.datetime(LocalDateTime.of(
						LocalDate.parse(item.getPubdate(), DateTimeFormatter.BASIC_ISO_DATE), 
						LocalTime.MIN))
				.price(item.getPrice())
				.salePrice(item.getDiscount())
				.build();
	}


	@Getter
	@Setter
	@NoArgsConstructor(access = AccessLevel.PACKAGE)
	@EqualsAndHashCode
	@ToString
	static class Item {

        private String title;
        private String link;
        private String image;
        private String author;
        private Integer price;
        private Integer discount;
        private String publisher;
        private String pubdate;
        private String isbn;
        private String description;

	}

}
