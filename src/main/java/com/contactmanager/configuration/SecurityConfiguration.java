package com.contactmanager.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//SPRING SECURITY CONFIGURATION
//WHATEVER WE ARE CONFIGURING IN SPRING SECURITY WE WILL CONFIGURE IT HAS BEAN

@Configuration
public class SecurityConfiguration {

	//SPRING SECURITY USES 'UserDetailsService'. SO WHENEVER WE PERFORM LOGIN IT USES 'UserDetailsService' TO FETCH USER.
	
	/*
	THE SERVICE HAS A METHOD loadUserByUserName(), IT CALLS THAT METHOD TO LOAD THE USER. THEN IT MATCHES THE PASSWORD OF LOADED USER AND LOGIN PASSWORD AND 
	IF IT IS MATCHES THEN IT PERFORMS SUCCESSFUL LOGIN.
	
	WE HAVE DIFFERENT TYPES OF SERVICES AVAILABLE. 
	
	IF WE SAYS THAT MY USER IS PRESENT IN MEMORY (TEMPORARY) THEN FOR THAT WE CAN USE 'InMemoryUserDetailsService'. 
	
	WITHOUT PASSWORD ENCODING WE CANNOT PERFORM LOGIN AS SPRING SECURITY REQUIRES PASSWORDS TO BE ENCODED USING A SPECIFIC PASSWORD ENCODER,
	E.G. BCRYPTPASSWORDENCODER
	
	BCRYPTPASSWORDENCODER IS A IMPLEMENTATION OF PASSWORDENCODER THAT USES THE BCRYPT STRONG HASHING FUNCTION.
	THERE'S NO MATHEMATICAL WAY TO RETRIEVE THE ORIGINAL PASSWORD FROM THE GENERATED HASH.
	TO COMPARE THE ENTERED PASSWORD AND DATABASE PASSWORD (ENCODED) WE NEED TO USE 'encoder.matches(password, user.getPassword());' method of BCryptPasswordEncoder CLASS.
	E.G. BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
		 encoder.matches(password, user.getPassword());  

	
	*/
	
	//CREATING USER AND PERFORMING LOGIN USING IN-MEMORY SERVICE
	@Bean
	UserDetailsService userDetailsService() {
		
		UserDetails user = User.withUsername("User").password("User").roles("USER", "ADMIN").build();
		UserDetails user1 = User.withUsername("User1").password("User1").roles("USER").build();
		
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new	InMemoryUserDetailsManager(user, user1);
		
		return inMemoryUserDetailsManager;
	}
	
	//RETURNING INSTANCE OF NoOpPasswordEncoder (FOR TESTING) SO NO NEED OF ENCODING THE PASSWORD. NOT RECOMMANDED FOR PRODUCTION.
	@Bean
	PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	
	
}
