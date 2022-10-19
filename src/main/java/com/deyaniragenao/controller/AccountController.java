package com.deyaniragenao.controller;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.deyaniragenao.dto.FinEntryDto;
import com.deyaniragenao.model.Account;
import com.deyaniragenao.model.Category;
import com.deyaniragenao.model.Expense;
import com.deyaniragenao.model.Income;
import com.deyaniragenao.model.User;
import com.deyaniragenao.service.AccountService;
import com.deyaniragenao.service.ExpenseService;
import com.deyaniragenao.service.IncomeService;
import com.deyaniragenao.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Account Controller handles requests and responses for all routes connected to the account. All paths are dependent 
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
	
	@ModelAttribute("user")
	public User getSessionUser() {
		String userEmail = getSessionUserEmail();
		User user = userService.findUserByEmail(userEmail);
		return user;
	}
	
	// helper method for retrieving current session user email 
	public String getSessionUserEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
	
	// Account Expenses 
	@GetMapping("/expenses/{id}")
	public String getAccountExpensesPage(@PathVariable(required=false) String id, Model model) {
		model.addAttribute("id", id);
		//log.info(id);
		Optional<Account> currAccData = accountService.findById(id);
		Account account = currAccData.get();
		model.addAttribute("account", account);
		
		Set<Expense> accExpenses = new LinkedHashSet<>();
		accExpenses = account.getExpenses();
		
		log.info(accExpenses.toString());
		model.addAttribute("expenses", accExpenses);
		
		FinEntryDto newExpense = new FinEntryDto();
		model.addAttribute("newExpense", newExpense);
		
		List<Category> expenseCategories = expenseService.getExpenseCategories();
		model.addAttribute("exCategories", expenseCategories);
		
		return "expense";
	}
	
	@PostMapping("/expenses/{id}")
	public String createExpense(@Valid @ModelAttribute("newExpense") FinEntryDto newExpense, 
			@PathVariable("id") String accId, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("newExpense", newExpense);
			return "/accounts/expenses/" + accId;
		}
		log.info(newExpense.getCategoryId().toString());
		log.info(accId);
		model.addAttribute("id", accId);
		expenseService.saveNewExpense(newExpense, accId, (User) model.getAttribute("user"));
		return "redirect:/accounts/expenses/" + accId;
	}
	
	// delete 
	@GetMapping("/expenses/{id}/delete/{expId}")
	public String deleteExpense(@PathVariable("id") String accId, @PathVariable("expId") Long expenseId, Model model) {
		log.info(accId);
		System.out.println(accId);
		expenseService.deleteExpense(expenseId);
		model.addAttribute("id", accId);
		return "redirect:/accounts/expenses/" + accId;
	}
	
	
	// Account Income 
	@GetMapping("/incomes/{id}")
	public String getAccountIncomesPage(@PathVariable(required = false) String id, Model model) {
		model.addAttribute("id", id);
		log.info(id);
		Optional<Account> currAccData = accountService.findById(id);
		Account account = currAccData.get();
		model.addAttribute("account", account);
		
		Set<Income> accIncomes = new LinkedHashSet<>();
		accIncomes = account.getIncomes();
		
		log.info(accIncomes.toString());
		model.addAttribute("incomes", accIncomes);
		
		FinEntryDto newIncome = new FinEntryDto();
		model.addAttribute("newIncome", newIncome);
		
		List<Category> incomeCategories = incomeService.getIncomeCategories();
		model.addAttribute("inCategories", incomeCategories);
		return "income";
	}
	
	@PostMapping("/incomes/{id}")
	public String createIncome(@Valid @ModelAttribute("newIncome") FinEntryDto newIncome, 
			@PathVariable("id") String accId, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("newIncome", newIncome);
			return "/accounts/incomes/" + accId;
		}
		log.info(newIncome.getCategoryId().toString());
		log.info(accId);
		model.addAttribute("id", accId);
		incomeService.saveNewIncome(newIncome, accId, (User) model.getAttribute("user"));
		return "redirect:/accounts/incomes/" + accId;
	}
	
	// delete 
	@GetMapping("/incomes/{id}/delete/{inId}")
	public String deleteIncome(@PathVariable("id") String accId, @PathVariable("inId") Long incomeId, Model model) {
		log.info(accId);
		incomeService.deleteIncome(incomeId);
		model.addAttribute("id", accId);
		return "redirect:/accounts/incomes/" + accId;
	}
	
	// Account Discretionary/ Wishlist 
	@GetMapping("/wishlist/{id}")
	public String getAccountWishlistPage(@PathVariable(required = false) String id, Model model) {
		
		return "wishlist";
	}
	
}
