package com.contactmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contactmanager.entity.User;
import com.contactmanager.helper.Helper;
import com.contactmanager.helper.LoggedInUserData;
import com.contactmanager.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	// UESR DASHBOARD PAGE
	@GetMapping("/dashboard")
	public String userDashboard() {
		return "user/dashboard";
	}

	// USER PROFILE PAGE
	@GetMapping("/profile")
	public String userProfile() {
		return "user/profile";
	}

}
