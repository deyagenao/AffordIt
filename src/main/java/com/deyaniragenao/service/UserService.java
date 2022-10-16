package com.deyaniragenao.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deyaniragenao.model.Account;
import com.deyaniragenao.model.Expense;
import com.deyaniragenao.model.Income;
import com.deyaniragenao.model.User;
import com.deyaniragenao.repository.AccountRepository;
import com.deyaniragenao.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired 
	AccountRepository accountRepository;
	
	public void saveNewUser(User user) {
		userRepository.save(user);
	}
	
	// Account
	public User addNewAccount(String id, Account acc) {
		Optional<User> userData = userRepository.findById(id);
		// Optional<Account> accData = accountRepository.findById(acc.getId());
		User user = userData.get();
		// Account account = accData.get();
		
		user.addAccount(acc);
		
		userRepository.save(user);
		// accountRepository.save(account);
		
		return user;
	}
	
	public Set<Account> findAccountsByUserId(String id) {
		return accountRepository.findAccountsByUserId(id);
	}
	
	// Expenses 
	public Expense addNewExpense(String id, Expense expense) {
		Optional<User> userData = userRepository.findById(id);
		User user = userData.get();
		
		user.addExpense(expense);
		userRepository.save(user);
		
		return expense;
	}
	// Income
	public Income addNewIncome(String id, Income income) {
		Optional<User> userData = userRepository.findById(id);
		User user = userData.get();
		
		user.addIncome(income);
		userRepository.save(user);
		
		return income;
	}
	// Discretionary 
	
}
