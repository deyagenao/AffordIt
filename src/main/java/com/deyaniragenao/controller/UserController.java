package com.deyaniragenao.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.deyaniragenao.dto.AccountDto;
import com.deyaniragenao.model.Account;
import com.deyaniragenao.model.User;
import com.deyaniragenao.service.AccountService;
import com.deyaniragenao.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/home")
@SessionAttributes("user")
@Slf4j
public class UserController {
	
	@Autowired
	UserService userService;
	@Autowired 
	AccountService accountService;
	
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
	
	@GetMapping("/")
	public String getUserHome(Model model, @ModelAttribute("user") User user) {
		Set<Account> userAccounts = new LinkedHashSet<>();
		userAccounts = user.getAccounts();
		log.info(userAccounts.toString());
		if(userAccounts.isEmpty()) {
			model.addAttribute("accStatus", "noAccounts");
		} else {
			Account currAcc = userAccounts.iterator().next();
			model.addAttribute("accStatus", "hasAccounts");
			model.addAttribute("accounts", userAccounts);
			model.addAttribute("currentAccount", currAcc);
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
	
	
	
//	========================================
	
	// put mapping for user info: maybe will need once UserDto is implemented- for validity 
	// no need to create delete mapping for user due to Spring REST - or maybe there should be one 
	// so that user can be redirected after changes/ deletions 
	
	@PutMapping("{id}/addAccount")
	public String addNewAccount(@ModelAttribute User user, @RequestBody Account account, Model model){
		User updatedUser = userService.addNewAccount(user.getId(), account);
		model.addAttribute("user", updatedUser);
		return "userhome";
	}

//	@GetMapping("/user/accounts")
//	public Set<Account> getUserAccounts(@ModelAttribute User user) {
//		return userService.findAccountsByUserId(user.getId());
//	}
	
	@GetMapping("/users/{id}/accounts/")
	@ResponseBody
	public Set<Account> getUserAccounts(@PathVariable String id) {
		return userService.findAccountsByUserId(id);
	}
}
