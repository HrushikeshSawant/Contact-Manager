package com.contactmanager.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

	public static String getLoggedInUserEmail(Authentication authentication) {
		
		String email = "";
		
		//IF LOGGEN IN USING GOOGLE OR GITHUB
		if(authentication instanceof OAuth2AuthenticationToken) {
			
			OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
			String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
			OAuth2User principal = (OAuth2User) authentication.getPrincipal();
			
			if(authorizedClientRegistrationId.equalsIgnoreCase("google")) {
				email = principal.getAttribute("email");		
				email = (email == null) ? principal.getAttribute("name").toString().concat("@test.com") : email;	
			}
			else if(authorizedClientRegistrationId.equalsIgnoreCase("github")) {
				email = principal.getAttribute("email");		
				email = (email == null) ? principal.getAttribute("login").toString().concat("@test.com") : email;
			}
			
		}
		//IF LOGGED IN USING EMAIL AND PASSWORD
		else {
			email = authentication.getName();
		}
		
		return email;
	}
	
}
