package com.demo.booksearch.search.keyword.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.getField;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.booksearch.search.keyword.domain.SearchKeyword;
import com.demo.booksearch.search.keyword.infrastructure.SearchKeywordRepositoryImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
//@DataJpaTest(
//		showSql = true, 
//		includeFilters = @Filter(
//				type = FilterType.ASSIGNABLE_TYPE, 
//				classes = QuerydslRepositorySupport.class))
public class SearchKeywordRepositoryImplTest {

	private static final String KEYWORD1 = "키워드1";
	private static final String KEYWORD2 = "키워드2";
	private static final String KEYWORD3 = "키워드3";


	@Autowired
	private SearchKeywordRepositoryImpl repository;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;


	@Before
	public void setUp() {
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.persist(SearchKeyword.of(KEYWORD1));
		entityManager.persist(SearchKeyword.of(KEYWORD2));

		entityManager.flush();
		entityManager.getTransaction().commit();
	}

	@Test
	public void testLoad() {
		repository.load();

		var searchKeywords = repository.findTop(2);

		assertThat(searchKeywords).hasSize(2);
	}

	@Test
	public void testIncreaseCount() {
		repository.load();

		repository.increaseSearchCount(KEYWORD1);
		repository.increaseSearchCount(KEYWORD1);
		repository.increaseSearchCount(KEYWORD2);
		repository.increaseSearchCount(KEYWORD3);

		var searchKeywords = repository.findTop(3);

		assertThat(searchKeywords).hasSize(3);
		assertThat(searchKeywords.get(0).getKeyword()).isEqualTo(KEYWORD1);
		assertThat(searchKeywords.get(0).getSearchCount()).isEqualTo(3);
		assertThat(searchKeywords.get(0).getIncrement()).isEqualTo(2);
		assertThat(searchKeywords.get(1).getKeyword()).isEqualTo(KEYWORD2);
		assertThat(searchKeywords.get(1).getSearchCount()).isEqualTo(2);
		assertThat(searchKeywords.get(1).getIncrement()).isEqualTo(1);
		assertThat(searchKeywords.get(2).getKeyword()).isEqualTo(KEYWORD3);
		assertThat(searchKeywords.get(2).getSearchCount()).isEqualTo(1);
		assertThat(searchKeywords.get(2).getIncrement()).isEqualTo(1);
	}

	@Test
	public void testFindTop() {
		repository.load();

		repository.increaseSearchCount(KEYWORD1);
		repository.increaseSearchCount(KEYWORD1);
		repository.increaseSearchCount(KEYWORD2);

		var searchKeywords1 = repository.findTop(1);

		assertThat(searchKeywords1).hasSize(1);
		assertThat(searchKeywords1.get(0).getKeyword()).isEqualTo(KEYWORD1);

		var searchKeywords2 = repository.findTop(2);

		assertThat(searchKeywords2).hasSize(2);
		assertThat(searchKeywords2.get(0).getKeyword()).isEqualTo(KEYWORD1);
		assertThat(searchKeywords2.get(1).getKeyword()).isEqualTo(KEYWORD2);

		var searchKeywords3 = repository.findTop(3);

		assertThat(searchKeywords3).hasSize(2);
		assertThat(searchKeywords3.get(0).getKeyword()).isEqualTo(KEYWORD1);
		assertThat(searchKeywords3.get(1).getKeyword()).isEqualTo(KEYWORD2);
	}

	@Test
	public void testFlush() {
		repository.load();

		repository.increaseSearchCount(KEYWORD1);
		repository.increaseSearchCount(KEYWORD1);
		repository.increaseSearchCount(KEYWORD2);
		repository.increaseSearchCount(KEYWORD3);

		repository.refresh();

		var searchKeywords = repository.findTop(3);

		assertThat(searchKeywords).hasSize(3);
		assertThat(searchKeywords.get(0).getKeyword()).isEqualTo(KEYWORD1);
		assertThat(searchKeywords.get(0).getSearchCount()).isEqualTo(3);
		assertThat(searchKeywords.get(0).getIncrement()).isEqualTo(0);
		assertThat(searchKeywords.get(1).getKeyword()).isEqualTo(KEYWORD2);
		assertThat(searchKeywords.get(1).getSearchCount()).isEqualTo(2);
		assertThat(searchKeywords.get(1).getIncrement()).isEqualTo(0);
		assertThat(searchKeywords.get(2).getKeyword()).isEqualTo(KEYWORD3);
		assertThat(searchKeywords.get(2).getSearchCount()).isEqualTo(1);
		assertThat(searchKeywords.get(2).getIncrement()).isEqualTo(0);
	}

	@After
	public void tearDown() {
		entityManager.getTransaction().begin();

		entityManager.createQuery("DELETE FROM SearchKeyword searchKeyword").executeUpdate();

		entityManager.flush();
		entityManager.getTransaction().commit();

		@SuppressWarnings("unchecked")
		var cache = (Map<String, SearchKeyword>) getField(repository, "cache");
		cache.clear();
	}

}
