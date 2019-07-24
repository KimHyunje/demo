package com.demo.booksearch.search.keyword.infrastructure;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.LockModeType;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import com.demo.booksearch.search.keyword.domain.CustomizedSearchKeywordRepository;
import com.demo.booksearch.search.keyword.domain.QSearchKeyword;
import com.demo.booksearch.search.keyword.domain.SearchKeyword;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class SearchKeywordRepositoryImpl extends QuerydslRepositorySupport implements CustomizedSearchKeywordRepository {

	private static final QSearchKeyword SEARCH_KEYWORD = QSearchKeyword.searchKeyword;


	private final TransactionTemplate transactionTemplate;

	private final Map<String, SearchKeyword> cache = new ConcurrentHashMap<>();

	private final Map<String, Object> properties = new HashMap<>();


	public SearchKeywordRepositoryImpl(TransactionTemplate transactionTemplate) {
		super(SearchKeyword.class);

		this.transactionTemplate = transactionTemplate;

		this.properties.put("javax.persistence.lock.timeout", 1000L);
	}


	/**
	 * SearchKeyword는 키워드별로 unique하다고 가정합니다.
	 */
	@PostConstruct
	public void load() {
		from(SEARCH_KEYWORD)
				.fetch()
				.stream()
				.forEach(searchKeyword -> cache.put(searchKeyword.getKeyword(), searchKeyword));
	}

	@Override
	public void increaseSearchCount(String searchedKeyword) {
		cache.compute(
				searchedKeyword, 
				(keyword, searchKeyword) -> {
					if (Objects.isNull(searchKeyword)) return SearchKeyword.of(keyword);

					searchKeyword.increaseSearchCount();
					return searchKeyword;
				});
	}

	@Override
	public List<SearchKeyword> findTop(int rank) {
		return cache.entrySet()
				.stream()
				.map(Entry::getValue)
				.sorted((searchKeyword1, searchKeyword2) -> {
					var searchCount1 = searchKeyword1.getSearchCount();
					var searchCount2 = searchKeyword2.getSearchCount();
					if (searchCount1 > searchCount2) {
						return -1;
					}
					else if (searchCount1 < searchCount2) {
						return 1;
					}
					else {
						return 0;
					}
				})
				.limit(rank)
				.collect(toList());
	}

	@Override
	public void refresh() {
		flush();
		cache.clear();
		load();
	}

	@PreDestroy
	public void flush() {
		cache.entrySet()
				.stream()
				.map(Entry::getValue)
				.filter(SearchKeyword::isFlushNeed)
				.forEach(searchKeyword -> transactionTemplate.execute(status -> saveWithLock(searchKeyword)));
	}

	private SearchKeyword saveWithLock(SearchKeyword searchKeyword) {
		var keyword = searchKeyword.getKeyword();
		var savedSearchKeyword = getEntityManager()
				.find(SearchKeyword.class, keyword, LockModeType.PESSIMISTIC_WRITE, properties);

		if (isNull(savedSearchKeyword)) {
			try {
				getEntityManager().persist(searchKeyword);
				return searchKeyword;
			}
			catch (DuplicateKeyException e) {
				log.warn("{}", e);
				return saveWithLock(searchKeyword);
			}
		}
		else {
			savedSearchKeyword.addSearchCount(searchKeyword.getIncrement());
			return getEntityManager().merge(savedSearchKeyword);
		}
	}

}
