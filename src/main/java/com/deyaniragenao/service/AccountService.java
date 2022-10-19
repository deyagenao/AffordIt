package com.deyaniragenao.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deyaniragenao.model.Account;
import com.deyaniragenao.model.Expense;
import com.deyaniragenao.model.Income;
import com.deyaniragenao.repository.AccountRepository;
import com.deyaniragenao.repository.DiscretionaryRepository;
import com.deyaniragenao.repository.ExpenseRepository;
import com.deyaniragenao.repository.IncomeRepository;

@Service
public class AccountService {
	
	AccountRepository accountRepository; 
	@Autowired
	ExpenseRepository expenseRepository;
	@Autowired
	IncomeRepository incomeRepository;
	@Autowired
	DiscretionaryRepository disRepository;
	
	// account
	public Optional<Account> findById(String id) {
		return accountRepository.findById(id);
	}
	
	// add new expense 
	public Expense addNewExpense(String id, Expense expense) {
		Optional<Account> accData = accountRepository.findById(id);
		Account acc = accData.get();
		
		acc.addExpense(expense);
		accountRepository.save(acc);
		
		return expense;
	}
	// show expense total 
	public BigDecimal getExpenseTotal(String id) {
		return expenseRepository.findExpenseTotalByAccountId(id);
	}
	// update and delete should be handled by Spring REST, or may need to 
	// add a method from the account side
	

	// add new income 
	public Income addNewIncome(String id, Income income) {
		Optional<Account> accData = accountRepository.findById(id);
		Account acc = accData.get();
		
		acc.addIncome(income);
		accountRepository.save(acc);
		
		return income;
	}
	public BigDecimal getIncomeTotal(String id) {
		return incomeRepository.findIncomeTotalByAccountId(id);
	}
	
//	discretionary related
	
	
	@Autowired
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
}
