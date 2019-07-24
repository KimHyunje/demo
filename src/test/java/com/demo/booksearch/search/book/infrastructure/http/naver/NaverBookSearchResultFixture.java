package com.demo.booksearch.search.book.infrastructure.http.naver;

import java.util.List;

import com.demo.booksearch.search.book.infrastructure.http.naver.NaverBookSearchResult;
import com.demo.booksearch.search.book.infrastructure.http.naver.NaverBookSearchResult.Item;

public class NaverBookSearchResultFixture {

	private NaverBookSearchResultFixture() {
	}


	public static NaverBookSearchResult naverBookSearchResult() {
		var searchResult = new NaverBookSearchResult();
		searchResult.setTotal(100L);
		searchResult.setStart(1);
		searchResult.setDisplay(10);
		searchResult.setItems(items());
		return searchResult;
	}

	private static List<Item> items() {
		Item item1 = new Item();
        item1.setTitle("제목1");
        item1.setLink("http://book.naver.com/bookdb/book_detail.php?bid=1");
        item1.setImage("https://bookthumb-phinf.pstatic.net/1.jpg");
        item1.setAuthor("저자1");
        item1.setPrice(10000);
        item1.setDiscount(9000);
        item1.setPublisher("출판사1");
        item1.setPubdate("20190101");
        item1.setIsbn("8996991341 9788996991342");
        item1.setDescription("컨텐츠1");

		Item item2 = new Item();
        item2.setTitle("제목2");
        item2.setLink("http://book.naver.com/bookdb/book_detail.php?bid=2");
        item2.setImage("https://bookthumb-phinf.pstatic.net/2.jpg");
        item2.setAuthor("저자2");
        item2.setPrice(20000);
        item2.setDiscount(18000);
        item2.setPublisher("출판사2");
        item2.setPubdate("20190202");
        item2.setIsbn("8996991341 9788996991343");
        item2.setDescription("컨텐츠2");

		return List.of(item1, item2);
	}

}
