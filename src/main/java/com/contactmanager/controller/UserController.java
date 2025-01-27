package com.contactmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contactmanager.helper.Helper;

@Controller
@RequestMapping("/user")
public class UserController {

	private Logger log = LoggerFactory.getLogger(UserController.class);
	
	//UESR DASHBOARD PAGE
	@GetMapping("/dashboard")
	public String userDashboard(Authentication authentication)
	{
		String email = Helper.getLoggedInUserEmail(authentication);
		log.info(email);
		return "user/dashboard";
	}
	
	//USER PROFILE PAGE
	@GetMapping("/profile")
	public String userProfile()
	{
		return "user/profile";
	}
	
	
}
