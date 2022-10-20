package com.deyaniragenao.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.deyaniragenao.dto.UserDto;
import com.deyaniragenao.model.User;
import com.deyaniragenao.service.UserService;

/**
 * MainController is the controller class that handles requests to the initial endpoints 
 * of the application, including user sign in and user sign up. 
 * @author deyaniragenao
 *
 */
@Controller
public class MainController {
	
	@Autowired
	UserService userService;
	
	/**
	 * Initial get mapping - returns the index page
	 * @return index view
	 */
	@GetMapping("/")
	public String getIndex() {
		return "index";
	}
	
	/**
	 * GetMapping: Returns the sign in view 
	 * @return sign in view
	 */
	@GetMapping("/signin")
	public String getSignIn() {
		return "sign_in";
	}
	
	/**
	 * Get Mapping: Creates a new UserDto object and adds it to the model for the sign up form.
	 * Returns the sign up page 
	 * @param model
	 * @return sign up view
	 */
	@GetMapping("/signup")
	public String getSignUp(Model model) {
		UserDto user = new UserDto();
		model.addAttribute("user", user);
		return "sign_up";
	}
	
	/**
	 * Post Mapping: Handles the creation of a new user. If any fields are invalid, uses the BindingResult
	 * object to reject the result and return the sign up form. 
	 * If there are no errors, the new user is persisted to the database and redirected to 
	 * the sign in page.  
	 * @param userDto
	 * @param result
	 * @param model
	 * @return signin url 
	 */
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
	
	
}
