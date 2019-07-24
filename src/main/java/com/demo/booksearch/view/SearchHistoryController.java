package com.demo.booksearch.view;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchHistoryController {

	@GetMapping("/search-history")
	public ModelAndView searchHistory(@AuthenticationPrincipal UserDetails userDetails) {
		return new ModelAndView("search-history")
				.addObject("userId", userDetails.getUsername());
	}

}
