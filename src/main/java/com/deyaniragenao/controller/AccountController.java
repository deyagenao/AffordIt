package com.deyaniragenao.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.deyaniragenao.model.Account;
import com.deyaniragenao.model.Expense;
import com.deyaniragenao.service.AccountService;

@Controller
@SessionAttributes("account")
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@GetMapping("/user/account/expenses")
	public Set<Expense> getAccountExpenses(@ModelAttribute Account account){
		return accountService.findExpensesOfAccountById(account.getId());
	}
}
