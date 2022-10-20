package com.deyaniragenao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deyaniragenao.model.Category;

/**
 * Repository class for interacting with the Category Table in the database. Contains one custom method. 
 * @author deyaniragenao
 *
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	/**
	 * Accepts a string representing a category type and returns a list of all categories with that type
	 * @param type
	 * @return
	 */
	@Query("SELECT c FROM Category c WHERE c.type =?1")
	public List<Category> findCategoriesByType(String type);
	
}
