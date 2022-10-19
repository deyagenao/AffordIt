package com.deyaniragenao.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deyaniragenao.dto.FinEntryDto;
import com.deyaniragenao.model.Account;
import com.deyaniragenao.model.Category;
import com.deyaniragenao.model.Income;
import com.deyaniragenao.model.FinancialEntryFrequency;
import com.deyaniragenao.model.User;
import com.deyaniragenao.repository.AccountRepository;
import com.deyaniragenao.repository.CategoryRepository;
import com.deyaniragenao.repository.IncomeRepository;

/**
 * IncomeService is a service class that implements customized methods and business logic for interacting with income-related data. 
 * @author deyaniragenao
 *
 */
@Service
public class IncomeService {

	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	IncomeRepository incomeRepository;
	@Autowired 
	AccountRepository accountRepository;
	
	/**
	 * Accepts a newIncome with a data type of FinEntryDto. This data is then mapped to a valid Income object and persisted to the database.
	 * @param newIncome
	 * @param accId
	 * @param user
	 */
	public void saveNewIncome(FinEntryDto newIncome, String accId, User user) {
		Income income = new Income();
		income.setName(newIncome.getName());
		income.setAmount(newIncome.getAmount());
		switch(newIncome.getFrequency()) {
			case "weekly":
				income.setFrequency(FinancialEntryFrequency.WEEKLY);
				break;
			case "biweekly":
				income.setFrequency(FinancialEntryFrequency.BIWEEKLY);
				break;
			case "monthly":
				income.setFrequency(FinancialEntryFrequency.MONTHLY);
				break;
			case "annually":
				income.setFrequency(FinancialEntryFrequency.ANNUALLY);
				break;
		}
		income.setMonthlyAmount(income.calculateMonthlyAmount());
		income.setUser(user);
		
		Optional<Category> catData = categoryRepository.findById(newIncome.getCategoryId());
		income.setCategory(catData.get());
		
		Optional<Account> accData = accountRepository.findById(accId);
		accData.get().addIncome(income);
		user.addIncome(income);
		
		incomeRepository.save(income);
	}
	
	/**
	 * Deletes the income record with the given id 
	 * @param id
	 */
	public void deleteIncome(Long id) {
		incomeRepository.deleteById(id);
	}
	
	/**
	 * Returns the sum of the monthly amount for all income for a given account 
	 * @param id
	 * @return sum
	 */
	public BigDecimal getSumOfAccountIncome(String id) {
		return incomeRepository.findIncomeTotalByAccountId(id);
	}
	
	/**
	 * Returns a list of all records from the Categories table with a type of income 
	 * @return List<Category>
	 */
	public List<Category> getIncomeCategories(){
		return categoryRepository.findCategoriesByType("income");
	}
	
	
}
