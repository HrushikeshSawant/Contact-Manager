package com.contactmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.contactmanager.entity.User;
import com.contactmanager.helper.Helper;
import com.contactmanager.helper.LoggedInUserData;
import com.contactmanager.service.UserService;

@ControllerAdvice
public class RootController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	UserService userService;

	@ModelAttribute
	public void getLoggedInUserDetails(Model model, Authentication authentication) {

		if(authentication == null) {
			return;
		}
		
		String email = Helper.getLoggedInUserEmail(authentication);
		User user = userService.getUserByEmail(email);
		log.info(user.toString());
		LoggedInUserData loggedInUserData = new LoggedInUserData(user.getName(), email, user.getProfilePic(),
				user.getPhone(), user.isEnabled(), user.isEmailVerified(), user.isPhoneVerified(), user.getContacts(),
				user.getSentEmail());
		model.addAttribute("loggedInUserData", loggedInUserData);
	}

}
