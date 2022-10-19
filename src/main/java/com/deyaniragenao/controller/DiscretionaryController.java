package com.deyaniragenao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"user", "accounts", "currentAccount"})
@RequestMapping("/wishlist")
public class DiscretionaryController {

	@GetMapping("/")
	public String getDiscretionaryPage() {
		return "wishlist";
	}
}
