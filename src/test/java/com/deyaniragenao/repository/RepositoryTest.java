package com.deyaniragenao.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import com.deyaniragenao.model.Category;

//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RepositoryTest {

	@Autowired 
	TestEntityManager testEntityManager;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@ParameterizedTest
	@ValueSource(strings = {"expense", "income"})
	void findCategoriesByTypeTest(String type) {
		categoryRepository.save(new Category("Other", type));
		List<Category> categories = categoryRepository.findCategoriesByType(type);
		assertTrue(categories.size() > 0);
	}
	
	

	
}
