package com.contactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contactmanager.entity.User;
import com.contactmanager.helper.Message;
import com.contactmanager.serviceImplementation.UserServiceImplementation;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	UserServiceImplementation userServiceImplementation;

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
		model.addAttribute("user", new User());
		return "signup";
	}
	
	
	@PostMapping("/register")
	public String register(@ModelAttribute("user") User user, @RequestParam(value = "agreement", defaultValue = "false") Boolean agreement, Model model, HttpSession session)
	{
		try
		{
			System.out.println(user.toString());
			System.out.println(agreement);
			
			model.addAttribute("title", "Sign Up | Contact Manager");
			
			if(!agreement)
			{
				session.setAttribute("agreementappend", "text-danger");
				session.setAttribute("message", new Message("*Please accept Terms & Conditions", "text-dark fw-bold"));
				return "redirect:/signup";
			}
			
			user.setRole("ROLE_USER");
			user.setStatus(true);
			
			//BECRYPT PASSWORD ENCODER USED TO ENCRYPT PASSWORD
			
			userServiceImplementation.register(user);
			
			session.setAttribute("user", new User());
			session.setAttribute("message", new Message("Registration Successful!!", "text-success fw-bold"));
			return "redirect:/signup";
		}
		catch(Exception e)
		{
			session.setAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong!! Please try again..", "text-dark fw-bold"));
			return "redirect:/signup";
		}
	}
	
}
