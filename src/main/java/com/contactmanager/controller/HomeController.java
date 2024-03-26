package com.contactmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(Model model)
	{
		model.addAttribute("title", "Free Contact Manager | Contact Manager");
		model.addAttribute("homeappend", "active");
		return "home";
	}
	
	@GetMapping("/about")
	public String about(Model model)
	{
		model.addAttribute("title", "About Page");
		return "about";
	}
	
	@GetMapping("/signup")
	public String signup(Model model)
	{
		model.addAttribute("title", "Sign Up | Contact Manager");
		model.addAttribute("signupappend", "active");
		return "signup";
	}
	
}
