package com.contactmanager.configuration;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.contactmanager.entity.User;
import com.contactmanager.helper.ApplicationConstants;
import com.contactmanager.helper.Providers;
import com.contactmanager.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
		DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
//		principal.getAttributes().forEach((key, value) -> log.info(key + " => " + value));
//		log.info(principal.getName());
//		principal.getAuthorities().forEach(System.out::println);
		
		User user = new User();
		user.setName(principal.getAttribute("name").toString());
		user.setEmail(principal.getAttribute("email").toString());
		user.setProfilePic(principal.getAttribute("picture").toString());
		user.setEnabled(true);
		user.setEmailVerified(principal.getAttribute("email_verified"));
		user.setProvider(Providers.GOOGLE);
		user.setProviderUserId(principal.getName());
		user.setRoleList(List.of(ApplicationConstants.ROLE_USER));
		
		Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
		if(existingUser.isEmpty()) {
			userRepository.save(user);
			log.info("User saved");
		}
		
		log.info("Out of if");
		
		response.sendRedirect("/user/dashboard");
		
	}

}
