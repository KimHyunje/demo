package com.demo.booksearch.search.book.interfaces.dto;

import static java.util.stream.Collectors.joining;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.ResourceSupport;

import com.demo.booksearch.search.book.domain.Book;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BooksResponse extends ResourceSupport {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;


	private List<BookElement> books = new ArrayList<>();


	public BooksResponse(Page<Book> books) {
		books.stream()
			.map(BookElement::new)
			.forEach(element -> this.books.add(element));
	}


	@Getter
	@EqualsAndHashCode
	@ToString
	private class BookElement {

		private String title;
		private String thumbnail;
		private String contents;
		private String isbn;
		private String authors;
		private String publisher;
		private String datetime;
		private Integer price;
		private Integer salePrice;


		BookElement(Book book) {
			this.title = book.getTitle();
			this.thumbnail = book.getThumbnail();
			this.contents = book.getContents();
			this.isbn = book.getIsbn();
			this.authors = book.getAuthors().stream().collect(joining(", "));
			this.publisher = book.getPublisher();
			this.datetime = book.getDatetime().format(DATE_TIME_FORMATTER);
			this.price = book.getPrice();
			this.salePrice = book.getSalePrice();
		}
		
	}

}
