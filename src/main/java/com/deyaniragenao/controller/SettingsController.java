package com.deyaniragenao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings")
public class SettingsController {

	// settings view 
	@GetMapping("/{id}")
	public String getSettingsPage(@PathVariable("id") String accId, Model model) {
		model.addAttribute("id", accId);
		return "settings";
	}
}
