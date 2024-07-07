package com.contactmanager.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.contactmanager.serviceImplementation.UserDetailsServiceImplementationSecurity;

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
	
	/*
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
	*/
	
	/*
	 IN BELOW CODE WILL RETURN daoAuthenticationProvider OBJECT BY SETTING UP UserDetailsService and AuthenticationManager
	  SO WHENEVER LOGIN REQUEST COMES, AT FIRST USER WILL BE LOADED FROM UserDetailsService. IT WILL LOADS THE CRETAED UserDetailsService WHICH IS 'UserDetailsServiceImplementationSecurity'.
	  THEN IT WILL LOADS THE USER FROM DATABASE IF PRESENT, OR ELSE IT WILL THROW AN EXCEPTION.
	  THEN AUTHENTICATION WILL  HAPPEN. IT WILL MATCH THE DATABASE PASSWORD AND ENTERED PASSWORD.
	*/
	
	@Autowired
	private UserDetailsServiceImplementationSecurity userDetailsServiceImplementationSecurity;
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		
		//DaoAuthenticationProvider HAS ALL METHODS USING WHICH WE CAN REGISTER THE SERVICE
		
		//PASSING OBJECT OF USER DETAILS SERVICE
		daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImplementationSecurity);
		
		//PASSING OBJECT OF PASSWORD ENCODER
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
		
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
