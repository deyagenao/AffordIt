package com.deyaniragenao.controller;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.deyaniragenao.dto.FinEntryDto;
import com.deyaniragenao.model.Account;
import com.deyaniragenao.model.Category;
import com.deyaniragenao.model.Discretionary;
import com.deyaniragenao.model.Expense;
import com.deyaniragenao.model.Income;
import com.deyaniragenao.model.User;
import com.deyaniragenao.service.AccountService;
import com.deyaniragenao.service.DiscretionaryService;
import com.deyaniragenao.service.ExpenseService;
import com.deyaniragenao.service.IncomeService;
import com.deyaniragenao.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Account Controller handles requests and responses for all routes connected to the current account. All paths are dependent 
 * on the current user account, and contain the user account number as a path variable. 
 * @author deyaniragenao
 *
 */
@Controller
@RequestMapping("/accounts")
@Slf4j
public class AccountController {
	
	@Autowired
	AccountService accountService;
	@Autowired
	UserService userService;
	@Autowired
	ExpenseService expenseService;
	@Autowired
	IncomeService incomeService;
	@Autowired
	DiscretionaryService discretionaryService;
	
	/**
	 * Retrieves the current principal from the Spring Security session and returns the user as a model attribute 
	 * to be used by other methods
	 * @return user
	 */
	@ModelAttribute("user")
	public User getSessionUser() {
		String userEmail = getSessionUserEmail();
		User user = userService.findUserByEmail(userEmail);
		return user;
	}
	
	/**
	 * Helper method for retrieving current user email from the Spring Security Session 
	 * @return email
	 */ 
	public String getSessionUserEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
	
	
	/**
	 * Returns the account's expenses page. Accepts the account id as a path variable and the model. Adds the current account, 
	 * current expenses, expenses total, and expense categories to the model. Also creates a new FinEntryDto for the 'add expense'
	 * form on the page.  
	 * @param id
	 * @param model
	 * @return expense view 
	 */
	@GetMapping("/expenses/{id}")
	public String getAccountExpensesPage(@PathVariable String id, Model model) {
		model.addAttribute("id", id);
		Optional<Account> currAccData = accountService.findById(id);
		Account account = currAccData.get();
		model.addAttribute("account", account);
		
		Set<Expense> accExpenses = new LinkedHashSet<>();
		accExpenses = account.getExpenses();
		log.info(accExpenses.toString());
		model.addAttribute("expenses", accExpenses);
		
		BigDecimal expenseTotal = expenseService.getSumOfAccountExpenses(id);
		model.addAttribute("expenseTotal", expenseTotal);
		
		FinEntryDto newExpense = new FinEntryDto();
		model.addAttribute("newExpense", newExpense);
		
		List<Category> expenseCategories = expenseService.getExpenseCategories();
		model.addAttribute("exCategories", expenseCategories);
		
		return "expense";
	}
	
	/**
	 * PostMapping for adding a new expense to the account. If there are any errors in the request, BindException is thrown.
	 * Exception is handled by checking if the BindingResult has errors and redirecting the user back to the page with a url
	 * error parameter
	 * @param newExpense
	 * @param result
	 * @param accId
	 * @param model
	 * @return expenses url
	 * @throws BindException
	 */
	@PostMapping("/expenses/{id}")
	public String createExpense(@Valid @ModelAttribute("newExpense") FinEntryDto newExpense, 
			 BindingResult result, @PathVariable("id") String accId, Model model) throws BindException {
		if(result.hasErrors()) {
			model.addAttribute("newExpense", new FinEntryDto());
			return "redirect:/accounts/expenses/" + accId + "?error";
		}
		log.info(newExpense.getCategoryId().toString());
		log.info(accId);
		model.addAttribute("id", accId);
		expenseService.saveNewExpense(newExpense, accId, (User) model.getAttribute("user"));
		return "redirect:/accounts/expenses/" + accId;
	}
	
	
	/**
	 *  Delete the expense with the id in the url path from the specified account. Redirects user back to the expenses page
	 * @param accId
	 * @param expenseId
	 * @param model
	 * @return url path
	 */
	@GetMapping("/expenses/{id}/delete/{expId}")
	public String deleteExpense(@PathVariable("id") String accId, @PathVariable("expId") Long expenseId, Model model) {
		log.info(accId);
		System.out.println(accId);
		expenseService.deleteExpense(expenseId);
		model.addAttribute("id", accId);
		return "redirect:/accounts/expenses/" + accId;
	}
	
	

	/**
	 * Returns the account's income page. Accepts the account id as a path variable and the model. Adds the current account, 
	 * current income, income total, and income categories to the model. Also creates a new FinEntryDto for the 'add income'
	 * form on the page. 
	 * @param id
	 * @param model
	 * @return income view
	 */
	@GetMapping("/incomes/{id}")
	public String getAccountIncomesPage(@PathVariable String id, Model model) {
		model.addAttribute("id", id);
		log.info(id);
		Optional<Account> currAccData = accountService.findById(id);
		Account account = currAccData.get();
		model.addAttribute("account", account);
		
		Set<Income> accIncomes = new LinkedHashSet<>();
		accIncomes = account.getIncomes();
		
		log.info(accIncomes.toString());
		model.addAttribute("incomes", accIncomes);
		
		BigDecimal incomeTotal = incomeService.getSumOfAccountIncome(id);
		model.addAttribute("incomeTotal", incomeTotal);
		
		FinEntryDto newIncome = new FinEntryDto();
		model.addAttribute("newIncome", newIncome);
		
		List<Category> incomeCategories = incomeService.getIncomeCategories();
		model.addAttribute("inCategories", incomeCategories);
		return "income";
	}
	
	/**
	 * PostMapping for adding new income to the account. If there are any errors in the request, BindException is thrown.
	 * Exception is handled by checking if the BindingResult has errors and redirecting the user back to the page with a url
	 * error parameter
	 * @param newIncome
	 * @param result
	 * @param accId
	 * @param model
	 * @return url path
	 */
	@PostMapping("/incomes/{id}")
	public String createIncome(@Valid @ModelAttribute("newIncome") FinEntryDto newIncome, 
			 BindingResult result, @PathVariable("id") String accId, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("newIncome", new FinEntryDto());
			return "redirect:/accounts/incomes/" + accId + "?error";
		}
		log.info(newIncome.getCategoryId().toString());
		log.info(accId);
		model.addAttribute("id", accId);
		incomeService.saveNewIncome(newIncome, accId, (User) model.getAttribute("user"));
		return "redirect:/accounts/incomes/" + accId;
	}
	
	/**
	 *  Delete the income with the id in the url path from the specified account. Redirects user back to the income page
	 * @param accId
	 * @param incomeId
	 * @param model
	 * @return url path
	 */
	@GetMapping("/incomes/{id}/delete/{inId}")
	public String deleteIncome(@PathVariable("id") String accId, @PathVariable("inId") Long incomeId, Model model) {
		log.info(accId);
		incomeService.deleteIncome(incomeId);
		model.addAttribute("id", accId);
		return "redirect:/accounts/incomes/" + accId;
	}
	
	/**
	 * Returns the account's wishlist page. Accepts the account id as a path variable and the model. Adds the current account, 
	 * current wishlist/ discretionary items, and the account balance (income - expenses) to the model. Also creates a new Discretionary
	 * for the 'add wishlist item' form on the page. 
	 * Error handling for the case when income or expense totals may be null. 
	 * @param id
	 * @param model
	 * @return wishlist view 
	 */
	@GetMapping("/wishlist/{id}")
	public String getAccountWishlistPage(@PathVariable String id, Model model) {
		model.addAttribute("id", id);
		// retrieving current wishlist items 
		Optional<Account> currAccData = accountService.findById(id);
		Account account = currAccData.get();
		Set<Discretionary> accDiscretionary = new LinkedHashSet<>();
		accDiscretionary = account.getDiscretionaryItems();
		model.addAttribute("wishlistItems", accDiscretionary);
		
		// create discretionary object for form 
		Discretionary newWishlist = new Discretionary();
		model.addAttribute("newWishlist", newWishlist);
		
		// retrieving current balance 
		BigDecimal expenseTotal = expenseService.getSumOfAccountExpenses(id);
		BigDecimal incomeTotal = incomeService.getSumOfAccountIncome(id);
		BigDecimal balance;
		try{
			balance = incomeTotal.subtract(expenseTotal);
		} catch(NullPointerException npe) {
			balance = null;
		}
		model.addAttribute("balance", balance);
		
		return "wishlist";
	}
	
	/**
	 * PostMapping for creating a new wishlist item. Accepts request parameters from the wishlist form
	 * Redirects users back to the wishlist page. 
	 * @param id
	 * @param name
	 * @param desc
	 * @param amount
	 * @param user
	 * @param model
	 * @return url path 
	 */
	@PostMapping("/wishlist/{accId}")
	public String createDiscretionary(@PathVariable("accId") String id, 
			@RequestParam("name") String name,
			@RequestParam("description") String desc,
			@RequestParam("amount") BigDecimal amount,
			@ModelAttribute("user") User user,
			Model model) {
		model.addAttribute("id", id);
		Discretionary newWishlist = new Discretionary(name,amount, desc);
		discretionaryService.saveNewDiscretionary(newWishlist, user, id);
		return "redirect:/accounts/wishlist/" + id;
	}
	
	/**
	 * Deletes an item from the wishlist. Redirects users back to the wishlist view for the current account. 
	 * @param accId
	 * @param wishlistId
	 * @param model
	 * @return url path 
	 */
	@GetMapping("/wishlist/{id}/delete/{wId}")
	public String deleteWishlist(@PathVariable("id") String accId, @PathVariable("wId") Long wishlistId, Model model) {
		log.info(accId);
		discretionaryService.deleteDiscretionary(wishlistId);
		model.addAttribute("id", accId);
		return "redirect:/accounts/wishlist/" + accId;
	}
	
}
