package com.deyaniragenao.repository;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deyaniragenao.model.Expense;

/**
 * Repository class for interacting with the Expense Table in the database. Contains one custom method. 
 * @author deyaniragenao
 *
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{

	/**
	 * Finds the sum of monthly expenses for the account with the id passed as the parameter 
	 * Uses a custom query written in JPQL to query the appropriate data. 
	 * @param id
	 * @return sum
	 */
	@Query("SELECT SUM(e.monthlyAmount) FROM Expense e "
			+ "GROUP BY e.account.id "
			+ "HAVING e.account.id =?1")
	BigDecimal findExpenseTotalByAccountId(String id);
}
