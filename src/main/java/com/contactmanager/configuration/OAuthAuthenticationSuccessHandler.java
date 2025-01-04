package com.contactmanager.configuration;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
		
		String fetchedEmail = "";
		
		DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
		
		OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
		String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
		log.info(authorizedClientRegistrationId);
		
		User user = new User();
		
		if(authorizedClientRegistrationId.equalsIgnoreCase("google")) {
			
//			System.out.println("google");
//			principal.getAttributes().forEach((key, value) -> log.info(key + " => " + value));
//			log.info(principal.getName());
//			principal.getAuthorities().forEach(System.out::println);
			
			user.setName(principal.getAttribute("name").toString());
			fetchedEmail = principal.getAttribute("email");		
			fetchedEmail = (fetchedEmail == null) ? principal.getAttribute("name").toString().concat("@test.com") : fetchedEmail;
			user.setEmail(fetchedEmail);
			user.setProfilePic(principal.getAttribute("picture").toString());
			user.setEnabled(true);
			user.setEmailVerified(principal.getAttribute("email_verified"));
			user.setProvider(Providers.GOOGLE);
			user.setProviderUserId(principal.getName());
			user.setRoleList(List.of(ApplicationConstants.ROLE_USER));
			user.setPassword(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()));
			
		}
		else if(authorizedClientRegistrationId.equalsIgnoreCase("github")) {
			
//			System.out.println("github");
//			principal.getAttributes().forEach((key, value) -> log.info(key + " => " + value));
//			log.info(principal.getName());
//			principal.getAuthorities().forEach(System.out::println);
			
			user.setName(principal.getAttribute("login").toString());	
			fetchedEmail = principal.getAttribute("email");		
			fetchedEmail = (fetchedEmail == null) ? principal.getAttribute("login").toString().concat("@test.com") : fetchedEmail;	
			user.setEmail(fetchedEmail);
			user.setProfilePic(principal.getAttribute("avatar_url").toString());
			user.setEnabled(true);
			user.setEmailVerified(false);
			user.setProvider(Providers.GITHUB);
			user.setProviderUserId(principal.getName());
			user.setRoleList(List.of(ApplicationConstants.ROLE_USER));
			user.setPassword(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()));
		}
		
	
		Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
		if(existingUser.isEmpty()) {
			userRepository.save(user);
			log.info("User saved");
		}
		
		log.info("Out of if");
		
		response.sendRedirect("/user/dashboard");
		
	}

}
