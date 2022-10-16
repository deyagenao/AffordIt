package com.deyaniragenao.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deyaniragenao.model.Account;
import com.deyaniragenao.model.Expense;
import com.deyaniragenao.repository.AccountRepository;
import com.deyaniragenao.repository.ExpenseRepository;

@Service
public class AccountService {
	
	AccountRepository accountRepository; 
	@Autowired
	ExpenseRepository expenseRepository;
	
	public void saveAccount(Account account) {
		// how to get user here before saving?
		accountRepository.save(account);
	}
	
	public Set<Expense> findExpensesOfAccountById(String id) {
		return expenseRepository.findExpensesByAccountId(id);
	}
	
	@Autowired
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
}
