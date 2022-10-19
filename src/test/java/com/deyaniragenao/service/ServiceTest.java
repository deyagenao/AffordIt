package com.deyaniragenao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.deyaniragenao.model.Account;
import com.deyaniragenao.model.Category;
import com.deyaniragenao.model.Discretionary;
import com.deyaniragenao.model.Expense;
import com.deyaniragenao.model.FinancialEntryFrequency;
import com.deyaniragenao.model.Income;
import com.deyaniragenao.model.User;
import com.deyaniragenao.repository.AccountRepository;
import com.deyaniragenao.repository.CategoryRepository;
import com.deyaniragenao.repository.DiscretionaryRepository;
import com.deyaniragenao.repository.ExpenseRepository;
import com.deyaniragenao.repository.IncomeRepository;
import com.deyaniragenao.repository.UserRepository;

@SpringBootTest
public class ServiceTest {

	@Autowired 
	UserService userService;
	@Autowired 
	UserRepository userRepository;
	@Autowired 
	AccountService accountService;
	@Autowired 
	AccountRepository accountRepository;
	@Autowired 
	ExpenseService expenseService;
	@Autowired 
	ExpenseRepository expenseRepository;
	@Autowired 
	IncomeService incomeService;
	@Autowired 
	IncomeRepository incomeRepository;
	@Autowired 
	DiscretionaryService discretionaryService;
	@Autowired 
	DiscretionaryRepository discretionaryRepository;
	@Autowired
	CategoryRepository categoryRepository;
	
	private User user = new User("Andrea", "Herrera", "and@he.com", "1234");
	private Account account = new Account("Main Account");
	private Category categoryE = new Category("Rent", "expense");
	private Category categoryI = new Category("Pay", "income");
	
	@BeforeEach
	void saveValuesToDB() {
		userRepository.save(user);
		accountRepository.save(account);
	}
	
	@AfterEach
	void deleteValuesFromDB() {
		userRepository.delete(user);
		accountRepository.delete(account);
	}
	
	@Test
	void findUserByEmailTest() {
		User expectedUser = user;
		User actualUser = userService.findUserByEmail(expectedUser.getEmail());
		assertEquals(expectedUser.getEmail(), actualUser.getEmail());
	}
	
	@Test
	void findAccountByIdTest() {
		Account expectedAccount = account;
		Optional<Account> actualAccData = accountService.findById(expectedAccount.getId());
		Account actualAccount = actualAccData.get();
		assertEquals(expectedAccount.getId(), actualAccount.getId());
	}
	
	@Test
	void deleteDiscretionaryTest() {
		Discretionary disc = new Discretionary("shoes", new BigDecimal("10.00"), "new shoes");
		disc.setAccount(account);
		disc.setUser(user);
		discretionaryRepository.save(disc);
		
		discretionaryService.deleteDiscretionary(disc.getId());
		assertTrue(discretionaryRepository.findById(disc.getId()).isEmpty());

	}
	
	@Test
	void deleteExpenseTest() {
		// set up
		categoryRepository.save(categoryE);
		Expense exp1 = new Expense(); 
		exp1.setName("Expense 1");
		exp1.setAmount(new BigDecimal("9.99"));
		exp1.setFrequency(FinancialEntryFrequency.MONTHLY);
		exp1.setMonthlyAmount(exp1.calculateMonthlyAmount());
		exp1.setCategory(categoryE);
		exp1.setUser(user);
		exp1.setAccount(account);
		expenseRepository.save(exp1);
		
		//check 
		expenseService.deleteExpense(exp1.getId());
		assertTrue(expenseRepository.findById(exp1.getId()).isEmpty());
		
		//After test
		categoryRepository.deleteById(categoryE.getId());
		
	}
	
	@Test 
	void deleteIncomeTest() {
		categoryRepository.save(categoryI);
		Income in1 = new Income(); 
		in1.setName("Income 1");
		in1.setAmount(new BigDecimal("9.99"));
		in1.setFrequency(FinancialEntryFrequency.MONTHLY);
		in1.setMonthlyAmount(in1.calculateMonthlyAmount());
		in1.setCategory(categoryI);
		in1.setUser(user);
		in1.setAccount(account);
		incomeRepository.save(in1);
		
		//check
		incomeService.deleteIncome(in1.getId());
		assertTrue(incomeRepository.findById(in1.getId()).isEmpty());
		
		//After Test 
		categoryRepository.deleteById(categoryI.getId());
	}
	
}
