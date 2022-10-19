package com.deyaniragenao.controller;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.deyaniragenao.dto.AccountDto;
import com.deyaniragenao.model.Account;
import com.deyaniragenao.model.User;
import com.deyaniragenao.service.AccountService;
import com.deyaniragenao.service.ExpenseService;
import com.deyaniragenao.service.IncomeService;
import com.deyaniragenao.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller class for handling all requests and responses from the user home page. 
 * @author deyaniragenao
 *
 */
@Controller
@RequestMapping("/home")
@SessionAttributes("user")
@Slf4j
public class UserController {
	
	@Autowired
	UserService userService;
	@Autowired 
	AccountService accountService;
	@Autowired
	ExpenseService expenseService;
	@Autowired
	IncomeService incomeService;
	
	/**
	 * Sets the model attribute of User for all other methods in the UserController class. 
	 * Gets the user email by using helper method and then locates this user from the database. 
	 * @return User user
	 */
	@ModelAttribute("user")
	public User getSessionUser() {
		String userEmail = getSessionUserEmail();
		User user = userService.findUserByEmail(userEmail);
		return user;
	}
	
	/**
	 *  Helper method for retrieving current session user email 
	 * @return String email
	 */
	public String getSessionUserEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
	
	@GetMapping("/")
	public String getUserHome(Model model, @ModelAttribute("user") User user) throws NullPointerException {
		Set<Account> userAccounts = new LinkedHashSet<>();
		userAccounts = user.getAccounts();
		log.info(userAccounts.toString());
		if(userAccounts.isEmpty()) {
			model.addAttribute("accStatus", "noAccounts");
		} else {
			Account currAcc = userAccounts.iterator().next();
			BigDecimal expenseTotal = expenseService.getSumOfAccountExpenses(currAcc.getId());
			BigDecimal incomeTotal = incomeService.getSumOfAccountIncome(currAcc.getId());
			BigDecimal balance;
			try{
				balance = incomeTotal.subtract(expenseTotal);
			} catch(NullPointerException npe) {
				balance = null;
			}
			model.addAttribute("accStatus", "hasAccounts");
			model.addAttribute("accounts", userAccounts);
			model.addAttribute("currentAccount", currAcc);
			model.addAttribute("expenseTotal", expenseTotal);
			model.addAttribute("incomeTotal", incomeTotal);
			model.addAttribute("balance", balance);
			log.info(currAcc.getName());
		}
		return "userhome";
	}
	
	

	@GetMapping("/addAccount")
	public String getAddAccountForm(@ModelAttribute User user, Model model) {
		AccountDto accDto = new AccountDto();
		model.addAttribute(user);
		model.addAttribute("account", accDto);
		model.addAttribute("accStatus", "addAccount");
		return "userhome";
	}
	
	@PostMapping("/addAccount")
	public String addAccount(@ModelAttribute User user, @RequestParam("accountNo") String accNo, Model model) {
		Optional<Account> accountData = accountService.findById(accNo);
		if(accountData.isEmpty()) {
			return "redirect:/home/addAccount?error";
		}
		Account account = accountData.get();
		userService.addNewAccount(user.getId(), account);
		model.addAttribute("currentAccount", account);
		model.addAttribute("user", user);
		return "redirect:/home/";
	}
	
	@PostMapping("/createNewAccount")
	public String createNewAccount(@ModelAttribute User user, @Valid @ModelAttribute("account") AccountDto accInfo, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("account", accInfo);
			return "redirect:/home/addAccount?accError";
		}
		Account account = new Account();
		account.setName(accInfo.getName());
		if(accInfo.getDescription() != null) {
			account.setDescription(accInfo.getDescription());
		}
		
		User updatedUser = userService.addNewAccount(user.getId(), account);
		model.addAttribute("user", updatedUser);
		model.addAttribute("currentAccount", account);
		
		return "redirect:/home/";
	}
	
}
