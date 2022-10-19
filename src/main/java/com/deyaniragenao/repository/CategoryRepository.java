package com.deyaniragenao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.deyaniragenao.model.Category;

@RepositoryRestResource
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	@Query("SELECT c FROM Category c WHERE c.type =?1")
	public List<Category> findCategoriesByType(String type);
	
}
