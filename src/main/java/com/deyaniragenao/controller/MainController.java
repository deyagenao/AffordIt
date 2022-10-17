package com.deyaniragenao.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.deyaniragenao.dto.UserDto;
import com.deyaniragenao.model.User;
import com.deyaniragenao.service.UserService;

@Controller
@SessionAttributes("user")
public class MainController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public String getIndex() {
		return "index";
	}
	
	@GetMapping("/signin")
	public String getSignIn() {
		return "signin";
	}
	
	// might not be necessary due to spring security
//	@PostMapping("/signin")
//	public String signIn() {
//		
//		return "redirect:/userhome";
//	}
	
	@GetMapping("/signup")
	public String getSignUp(Model model) {
		UserDto user = new UserDto();
		model.addAttribute("user", user);
		return "signup";
	}
	
	@PostMapping("/signup")
	public String registerNewUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
		User existingUser = userService.findUserByEmail(userDto.getEmail());
		if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
			result.rejectValue("email", null, "This email is already registered with an account.");
		}
		if(result.hasErrors()) {
			model.addAttribute("user", userDto);
			return "/signup";
		}
		userService.saveNewUser(userDto);
		return "redirect:/signin?success";
	}
	
	@GetMapping("/home")
	public String getUserHome() {
		return "userhome";
	}
}
