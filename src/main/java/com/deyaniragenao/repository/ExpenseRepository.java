package com.deyaniragenao.repository;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deyaniragenao.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{

	@Query("SELECT e FROM Expense e WHERE e.account.id =?1")
	Set<Expense> findExpensesByAccountId(String id);

	@Query("SELECT SUM(e.monthlyAmount) FROM Expense e "
			+ "GROUP BY e.account "
			+ "HAVING e.account =?1")
	BigDecimal findExpenseTotalByAccountId(String id);
}
