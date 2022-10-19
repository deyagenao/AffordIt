package com.deyaniragenao.repository;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deyaniragenao.model.Income;

/**
 * Repository class for interacting with the data in the Income Table. Contains one custom method
 * @author deyaniragenao
 *
 */
@Repository
public interface IncomeRepository extends JpaRepository<Income, Long>{

	/**
	 * Finds the sum of monthly income for the account with the given id. 
	 * Uses a custom query written in JPQL to query the appropriate data. 
	 * @param id
	 * @return sum
	 */
	@Query("SELECT SUM(i.monthlyAmount) FROM Income i "
			+ "GROUP BY i.account.id "
			+ "HAVING i.account.id =?1")
	BigDecimal findIncomeTotalByAccountId(String id);
}
