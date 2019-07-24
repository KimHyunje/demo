package com.demo.booksearch.search.keyword.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
@ToString
@Entity
public class SearchKeyword {

	@Getter
	@Id
	private String keyword;

	@Getter
	private long searchCount;

	@Getter
	@Transient
	private int increment = 0;


	private SearchKeyword(String keyword) {
		this.keyword = keyword;
		this.searchCount = 1;
		this.increment = 1;
	}


	public static SearchKeyword of(String keyword) {
		return new SearchKeyword(keyword);
	}

	public void increaseSearchCount() {
		searchCount++;
		increment++;
	}

	public boolean isFlushNeed() {
		return increment > 0;
	}

	public void addSearchCount(long increment) {
		searchCount += increment;
	}

	public void reset() {
		increment = 0;
	}

}
