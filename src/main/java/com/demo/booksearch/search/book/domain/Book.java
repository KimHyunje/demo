package com.demo.booksearch.search.book.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Book {

	private String title;
	private String thumbnail;
	private String contents;
	private String isbn;
	private List<String> authors;
	private String publisher;
	private LocalDateTime datetime;
	private Integer price;
	private Integer salePrice;


	@Builder
	private Book(
			String title, 
			String thumbnail, 
			String contents, 
			String isbn, 
			List<String> authors, 
			String publisher,
			LocalDateTime datetime, 
			Integer price, 
			Integer salePrice) {

		this.title = title;
		this.thumbnail = thumbnail;
		this.contents = contents;
		this.isbn = isbn;
		this.authors = authors;
		this.publisher = publisher;
		this.datetime = datetime;
		this.price = price;
		this.salePrice = salePrice;
	}

}
