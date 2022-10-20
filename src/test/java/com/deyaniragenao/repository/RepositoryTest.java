package com.deyaniragenao.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.deyaniragenao.model.Account;
import com.deyaniragenao.model.Category;
import com.deyaniragenao.model.Expense;
import com.deyaniragenao.model.FinancialEntryFrequency;
import com.deyaniragenao.model.Income;
import com.deyaniragenao.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RepositoryTest {
	
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	ExpenseRepository expenseRepository;
	@Autowired
	IncomeRepository incomeRepository;
	
	private User user = new User("Andrea", "Herrera", "andrea@herrera.com", "1234");
	private Account account = new Account("Main Account");
	private Category categoryE = new Category("Rent", "expense");
	private Category categoryI = new Category("Pay", "income");
	
	@ParameterizedTest
	@ValueSource(strings = {"expense", "income"})
	void findCategoriesByTypeTest(String type) {
		categoryRepository.save(new Category("Other", type));
		List<Category> categories = categoryRepository.findCategoriesByType(type);
		assertTrue(categories.size() > 0);
	}
	
	@Test
	void findUserByEmailTest() {
		User expectedUser = user;
		userRepository.save(expectedUser);
		User actualUser = userRepository.findByEmail(expectedUser.getEmail());
		assertEquals(expectedUser.getEmail(), actualUser.getEmail());
	}
	
	@Test 
	void findExpenseTotalByAccountIdTest() {
		userRepository.save(user);
		accountRepository.save(account);
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
		
		BigDecimal total = expenseRepository.findExpenseTotalByAccountId(account.getId());
		assertTrue(total.equals(new BigDecimal("9.99")));
		
	}
	
	@Test
	void findIncomeTotalByAccountIdTest() {
		userRepository.save(user);
		accountRepository.save(account);
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
		
		BigDecimal total = incomeRepository.findIncomeTotalByAccountId(account.getId());
		assertTrue(total.equals(new BigDecimal("9.99")));
	}
	
	
	
}
