package com.deyaniragenao.service;

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

@Service
public class ExpenseService {

	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ExpenseRepository expenseRepository;
	@Autowired 
	AccountRepository accountRepository;
	
	
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
	
	public void deleteExpense(Long id) {
		expenseRepository.deleteById(id);
	}
	
	public List<Category> getExpenseCategories(){
		return categoryRepository.findCategoriesByType("expense");
	}
}
