package com.deyaniragenao.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.deyaniragenao.model.Account;
import com.deyaniragenao.model.User;
import com.deyaniragenao.service.UserService;

@Controller
@SessionAttributes("user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/signup/save")
	public String saveNewUser(User user) {
		userService.saveNewUser(user);
		return "redirect:/signin?success";
	}
	
	// put mapping for user info: maybe will need once UserDto is implemented- for validity 
	// no need to create delete mapping for user due to Spring REST - or maybe there should be one 
	// so that user can be redirected after changes/ deletions 
	
	@PutMapping("{id}/addAccount")
	public String addNewAccount(@PathVariable String id, @RequestBody Account account, Model model){
		User updatedUser = userService.addNewAccount(id, account);
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
