package com.demo.booksearch.search.book.interfaces;

import static com.demo.booksearch.search.book.domain.BookFixture.book;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.booksearch.search.book.application.BookApplicationService;
import com.demo.booksearch.search.book.domain.Book;
import com.demo.booksearch.search.book.interfaces.BookRestControllerV1;

@RunWith(SpringRunner.class)
@WebMvcTest(BookRestControllerV1.class)
public class BookRestControllerV1Test {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

	private static final String BOOKS_URL_V1 = "/v1/books";


	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookApplicationService applicationService;

	private Page<Book> books;

	@Before
	public void setUp() {
		books = new PageImpl<>(List.of(book(), book()), PageRequest.of(1, 2), 5);

		given(applicationService.search(any(), any(), any())).willReturn(books);
	}

	@Test
	@WithMockUser("userId")
	public void testSearch() throws Exception {
		var requestBuilder = get(BOOKS_URL_V1)
				.param("keyword", "키워드")
				.param("page", "1")
				.param("size", "2");

		mockMvc.perform(requestBuilder)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", hasSize(2)))
				.andExpect(jsonPath("$.books.*", hasSize(books.getNumberOfElements())))
				.andExpect(jsonPath("$.books[0].*", hasSize(9)))
				.andExpect(jsonPath("$.books[0].title", 
						is(books.getContent().get(0).getTitle())))
				.andExpect(jsonPath("$.books[0].thumbnail", 
						is(books.getContent().get(0).getThumbnail())))
				.andExpect(jsonPath("$.books[0].contents", 
						is(books.getContent().get(0).getContents())))
				.andExpect(jsonPath("$.books[0].isbn", 
						is(books.getContent().get(0).getIsbn())))
				.andExpect(jsonPath("$.books[0].authors.*", 
						hasSize(books.getContent().get(0).getAuthors().size())))
				.andExpect(jsonPath("$.books[0].publisher", 
						is(books.getContent().get(0).getPublisher())))
				.andExpect(jsonPath("$.books[0].datetime", 
						is(books.getContent().get(0).getDatetime().format(DATE_TIME_FORMATTER))))
				.andExpect(jsonPath("$.books[0].price", 
						is(books.getContent().get(0).getPrice())))
				.andExpect(jsonPath("$.books[0].salePrice", 
						is(books.getContent().get(0).getSalePrice())))
				.andExpect(jsonPath("$.books[1].*", hasSize(9)))
				.andExpect(jsonPath("$.books[1].title", 
						is(books.getContent().get(1).getTitle())))
				.andExpect(jsonPath("$.books[1].thumbnail", 
						is(books.getContent().get(1).getThumbnail())))
				.andExpect(jsonPath("$.books[1].contents", 
						is(books.getContent().get(1).getContents())))
				.andExpect(jsonPath("$.books[1].isbn", 
						is(books.getContent().get(1).getIsbn())))
				.andExpect(jsonPath("$.books[1].authors.*", 
						hasSize(books.getContent().get(1).getAuthors().size())))
				.andExpect(jsonPath("$.books[1].publisher", 
						is(books.getContent().get(1).getPublisher())))
				.andExpect(jsonPath("$.books[1].datetime", 
						is(books.getContent().get(1).getDatetime().format(DATE_TIME_FORMATTER))))
				.andExpect(jsonPath("$.books[1].price", 
						is(books.getContent().get(1).getPrice())))
				.andExpect(jsonPath("$.books[1].salePrice", 
						is(books.getContent().get(0).getSalePrice())))
				.andExpect(jsonPath("$._links.*", hasSize(3)));
	}

}
