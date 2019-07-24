package com.demo.booksearch.search.book.interfaces;

import static com.demo.booksearch.search.book.domain.BookFixture.book;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.demo.booksearch.search.book.domain.Book;
import com.demo.booksearch.search.book.interfaces.BookAssembler;

public class BookAssemblerTest {

	private BookAssembler assembler;

	private String keyword = "키워드";
	private Pageable pageableFirst = PageRequest.of(0, 2);
	private Pageable pageableSecond = PageRequest.of(1, 2);
	private Pageable pageableThird = PageRequest.of(2, 2);

	private Page<Book> booksOfFirst;
	private Page<Book> booksOfSecond;
	private Page<Book> booksOfThird;


	@Before
	public void setUp() {
		booksOfFirst = new PageImpl<>(List.of(book(), book()), pageableFirst, 5);
		booksOfSecond = new PageImpl<>(List.of(book(), book()), pageableSecond, 5);
		booksOfThird = new PageImpl<>(List.of(book()), pageableThird, 5);
	}

	@Test
	public void testFirstPage() {
		assembler = new BookAssembler(keyword);
		var booksResponse = assembler.toResource(booksOfFirst);

		assertThat(booksResponse.getBooks()).hasSameSizeAs(booksOfFirst);
		assertThat(booksResponse.getLink("prev")).isNull();
		assertThat(booksResponse.getLink("next")).isNotNull();
	}

	@Test
	public void testSecondPage() {
		assembler = new BookAssembler(keyword);
		var booksResponse = assembler.toResource(booksOfSecond);

		assertThat(booksResponse.getBooks()).hasSameSizeAs(booksOfSecond);
		assertThat(booksResponse.getLink("prev")).isNotNull();
		assertThat(booksResponse.getLink("next")).isNotNull();
	}

	@Test
	public void testLastPage() {
		assembler = new BookAssembler(keyword);
		var booksResponse = assembler.toResource(booksOfThird);

		assertThat(booksResponse.getBooks()).hasSameSizeAs(booksOfThird);
		assertThat(booksResponse.getLink("prev")).isNotNull();
		assertThat(booksResponse.getLink("next")).isNull();
	}

}
