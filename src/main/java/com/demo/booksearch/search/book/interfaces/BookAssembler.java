package com.demo.booksearch.search.book.interfaces;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.demo.booksearch.search.book.domain.Book;
import com.demo.booksearch.search.book.interfaces.dto.BooksResponse;

public class BookAssembler extends ResourceAssemblerSupport<Page<Book>, BooksResponse>{

	private final String keyword;

	public BookAssembler() {
		this(null);
	}

	public BookAssembler(String keyword) {
		super(BookRestControllerV1.class, BooksResponse.class);

		this.keyword = keyword;
	}


	@Override
	public BooksResponse toResource(Page<Book> books) {
		var responseBody = new BooksResponse(books);
		var links = createLinks(books);
		responseBody.add(links);
		return responseBody;
	}

	private List<Link> createLinks(Page<Book> books) {
		List<Link> links = new ArrayList<>();
		if (books.hasPrevious()) {
			Link prevLink = createLink(books.previousPageable(), "prev");
			links.add(prevLink);
		}

		Link selfLink = createLink(books.getPageable(), "self");
		links.add(selfLink);

		if (books.hasNext()) {
			Link nextLink = createLink(books.nextPageable(), "next");
			links.add(nextLink);
		}
		return links;
	}

	private Link createLink(Pageable pageable, String rel) {
		int pageNumber = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();

		return linkTo(
				methodOn(BookRestControllerV1.class)
				.search(null, keyword, pageNumber, pageSize))
				.withRel(rel);
	}

}
