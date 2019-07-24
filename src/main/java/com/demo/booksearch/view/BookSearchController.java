package com.demo.booksearch.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BookSearchController {

	@RequestMapping(path = "/book-search", method = { RequestMethod.GET, RequestMethod.POST })
	public String bookSearch() {
		return "book-search";
	}

}
