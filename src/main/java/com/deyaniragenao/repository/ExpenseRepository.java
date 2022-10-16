package com.deyaniragenao.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deyaniragenao.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{

//	@Query("SELECT e FROM Expense e WHERE e.account.id = :id") --> but must use @Param in the method parameters for this to work?
	@Query("SELECT e FROM Expense e WHERE e.account.id =?1")
	Set<Expense> findExpensesByAccountId(String id);
}
