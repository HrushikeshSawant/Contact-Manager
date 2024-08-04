package com.contactmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	//UESR DASHBOARD PAGE
	@GetMapping("/dashboard")
	public String userDashboard()
	{
		return "user/dashboard";
	}
	
	//USER PROFILE PAGE
	@GetMapping("/profile")
	public String userProfile()
	{
		return "user/profile";
	}
	
	
}
