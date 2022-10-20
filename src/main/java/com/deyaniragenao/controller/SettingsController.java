package com.deyaniragenao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Settings Controller is in progress. Handles request to the settings endpoints. 
 * @author deyaniragenao
 *
 */
@Controller
@RequestMapping("/settings")
public class SettingsController {

	/**
	 * Get the Settings page for the account specified by the id in the url path.
	 * @param accId
	 * @param model
	 * @return settings view
	 */
	@GetMapping("/{id}")
	public String getSettingsPage(@PathVariable("id") String accId, Model model) {
		model.addAttribute("id", accId);
		return "settings";
	}
}
