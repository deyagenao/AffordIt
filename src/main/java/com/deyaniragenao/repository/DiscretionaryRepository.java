package com.deyaniragenao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deyaniragenao.model.Discretionary;

/**
* Repository class for interacting with the Discretionary Items Table in the database. 
* @author deyaniragenao
*
*/
@Repository
public interface DiscretionaryRepository extends JpaRepository<Discretionary, Long>{

}
