package com.deyaniragenao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
//@SessionAttributes("user")
public class MainController {
	
	@GetMapping("/")
	public String getIndex() {
		return "index";
	}
	
	@GetMapping("/signup")
	public String getSignUp() {
		return "signup";
	}
	
	@GetMapping("/signin")
	public String getSignIn() {
		return "signin";
	}
	
	@GetMapping("/home")
	public String getUserHome() {
		return "userhome";
	}
}
