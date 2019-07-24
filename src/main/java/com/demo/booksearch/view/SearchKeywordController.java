package com.demo.booksearch.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchKeywordController {

	@GetMapping("/search-keyword")
	public String searchKeyword() {
		return "search-keyword";
	}

}
