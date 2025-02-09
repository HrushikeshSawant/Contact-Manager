package com.contactmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.contactmanager.entity.User;
import com.contactmanager.helper.ApplicationConstants;
import com.contactmanager.helper.Message;
import com.contactmanager.helper.UserForm;
import com.contactmanager.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Value("${default.image.url}")
	private String defaultImgUrl;

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
		model.addAttribute("title", "About Page | Contact Manager");
		model.addAttribute("aboutappend", "active");
		return "about";
	}
	
	@GetMapping("/signup")
	public String signup(UserForm userForm, Model model)
	{
		model.addAttribute("title", "Sign Up | Contact Manager");
		model.addAttribute("signupappend", "active");
		model.addAttribute("userForm", userForm);
		return "signup";
	}
	
	
	@PostMapping("/register")
	public String register(@Valid UserForm userForm, BindingResult result, HttpSession session)
	{
		try
		{
			System.out.println(userForm.toString());
			System.out.println(result.hasErrors());
			if(result.hasErrors()){
				System.out.println(result.getAllErrors().toString());
				return "signup";
			}
			
			if(userService.isUserExistsByEmail(userForm.getEmail())) {
				session.setAttribute("emailExists", new Message("Email already exists!!", "form-text text-danger px-1"));
				return "redirect:signup";
			}
			
			if(!userForm.getPassword().equalsIgnoreCase(userForm.getConfirmPassword())) {
				session.setAttribute("wrongPassword", new Message("Password & Confirm Password must be same!!", "form-text text-danger px-1"));
				return "redirect:signup";
			}
			
			User user = new User();
			user.setName(userForm.getName());
			user.setEmail(userForm.getEmail());
			//BECRYPT PASSWORD ENCODER USED TO ENCRYPT PASSWORD
			user.setPassword(passwordEncoder.encode(userForm.getPassword()));
			user.setProfilePic(defaultImgUrl);
			user.setRoleList(List.of(ApplicationConstants.ROLE_USER));
			
			userService.registerUser(user);
			System.out.println("User saved");
			
//			session.setAttribute("user", new User());
			session.setAttribute("message", new Message("Registration Successful!!", "text-success fw-bold"));
			return "redirect:signup";
		}
		catch(Exception e)
		{
			session.setAttribute("user", userForm);
			session.setAttribute("message", new Message("Something went wrong!! Please try again..", "text-dark fw-bold"));
			return "redirect:signup";
		}
	}
	
	@GetMapping("/login")
	public String login(Model model)
	{
		model.addAttribute("title", "Login | Contact Manager");
		model.addAttribute("loginappend", "active");
		return "login";
	}
	
}
