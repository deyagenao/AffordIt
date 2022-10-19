package com.deyaniragenao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deyaniragenao.model.Category;
import com.deyaniragenao.service.IncomeService;

@Controller
@RequestMapping("/incomes")
public class IncomeController {
	
	@Autowired
	IncomeService incomeService;
	
	// @GetMapping("/delete/{id}")
	
}
