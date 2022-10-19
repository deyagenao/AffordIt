package com.deyaniragenao.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deyaniragenao.dto.FinEntryDto;
import com.deyaniragenao.model.Account;
import com.deyaniragenao.model.Category;
import com.deyaniragenao.model.Expense;
import com.deyaniragenao.model.FinancialEntryFrequency;
import com.deyaniragenao.model.User;
import com.deyaniragenao.repository.AccountRepository;
import com.deyaniragenao.repository.CategoryRepository;
import com.deyaniragenao.repository.ExpenseRepository;

/**
 * ExpenseService is a service class that implements customized methods for interacting with expense-related data. 
 * @author deyaniragenao
 *
 */
@Service
public class ExpenseService {

	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ExpenseRepository expenseRepository;
	@Autowired 
	AccountRepository accountRepository;
	
	/**
	 * Accepts a newExpense with a data type of FinEntryDto. This data is then set to a valid Expense object and persisted to the database.
	 * @param newExpense
	 * @param accId
	 * @param user
	 */
	public void saveNewExpense(FinEntryDto newExpense, String accId, User user) {
		Expense expense = new Expense();
		expense.setName(newExpense.getName());
		expense.setAmount(newExpense.getAmount());
		switch(newExpense.getFrequency()) {
			case "weekly":
				expense.setFrequency(FinancialEntryFrequency.WEEKLY);
				break;
			case "biweekly":
				expense.setFrequency(FinancialEntryFrequency.BIWEEKLY);
				break;
			case "monthly":
				expense.setFrequency(FinancialEntryFrequency.MONTHLY);
				break;
			case "annually":
				expense.setFrequency(FinancialEntryFrequency.ANNUALLY);
				break;
		}
		expense.setMonthlyAmount(expense.calculateMonthlyAmount());
		expense.setUser(user);
		
		Optional<Category> catData = categoryRepository.findById(newExpense.getCategoryId());
		expense.setCategory(catData.get());
		
		Optional<Account> accData = accountRepository.findById(accId);
		accData.get().addExpense(expense);
		user.addExpense(expense);
		
		expenseRepository.save(expense);
	}
	
	/**
	 * Deletes an expense record with the id passed as an argument
	 * @param id
	 */
	public void deleteExpense(Long id) {
		expenseRepository.deleteById(id);
	}
	
	/**
	 * Returns the sum of the monthly amount for all expenses for a given account 
	 * @param id
	 * @return sum
	 */
	public BigDecimal getSumOfAccountExpenses(String id) {
		return expenseRepository.findExpenseTotalByAccountId(id);
	}
	
	/**
	 * Returns a list of all categories with a type of expense 
	 * @return List<Category>
	 */
	public List<Category> getExpenseCategories(){
		return categoryRepository.findCategoriesByType("expense");
	}
}
