package com.contactmanager.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.contactmanager.service.implementation.UserDetailsServiceImplementationSecurity;

//SPRING SECURITY CONFIGURATION
//WHATEVER WE ARE CONFIGURING IN SPRING SECURITY WE WILL CONFIGURE IT HAS BEAN

@Configuration
@EnableWebSecurity
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
	
	@Autowired
	private OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler;
	
	
	//CONFIGURATION OF AuthenticationProvider FOR SPRING SECURITY
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
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf(csrf -> csrf.disable());
		
		//CONFIGURED URL'S WHICH NEEDS TO BE PUBLIC AND WHICH NEEDS TO BE PROTECTED
		httpSecurity.authorizeHttpRequests(authorize -> 
			
			authorize.requestMatchers("/user/**").authenticated()
			.anyRequest().permitAll()
		);
		
		//DAFAULT FORM LOGIN
//		httpSecurity.formLogin(Customizer.withDefaults());
		
		//CUSTOMIZED FORM LOGIN
		httpSecurity.formLogin(formLogin -> 
			formLogin
			.loginPage("/login")
			.loginProcessingUrl("/authenticate")
			.defaultSuccessUrl("/user/dashboard")
//			.successForwardUrl("/user/profile")
//			.failureForwardUrl("/login?error=true")
			.failureUrl("/login?error=true")
			.usernameParameter("email")
			.passwordParameter("password")
			.permitAll()
		);
		
		httpSecurity.logout(logout -> 
			logout.logoutUrl("/logout")
			.logoutSuccessUrl("/login?logout=true")
			.permitAll()
		);
		
//		OAuth Configuration:
/*		
 * 		1.	Add OAuth2 Client dependency [spring-boot-starter-oauth2-client]
 * 			Here this application will act as a client and google server will act as a server from which we will be authorizing the users.
 * 		2.	Get Client Id & Client Secrets from Google Cloud Platform (GCP)
 * 			Create new project -> Add Name -> Create -> Select Project -> Select APIs & Services from Navigation Menu:
 * 				i.	OAuth Consent Screen:
 * 					Select User Type (Internal/External) -> Provide Application Information -> Application Logo -> Application Domain (http://localhost:8080) -> Provide Application Privacy Policy Link & Terms of Service Link if application is running live -> Authorize Domain (Provide if applicatin is live) ->
 * 					  Provide Developer Contact Information -> Save & Continue -> Provide Scopes -> Save & Continue -> Consent Screen Is Ready.
 * 				ii.	Credential Screen:
 * 					Create Credentials -> OAuth Client Id -> Provide Application Type -> Name -> Provide Authorized JavaScript Origins (URI: http://localhost:8080) ->Provide Authorized Redirect URI (URI: http://localhost:8080/login/oauth2/code/google) -> Create
 * 						Copy Client Id & Client Secret
 * 		3.	Configure application.properties file with for OAuth (name, id, secret, scope
 * 		4.	Do OAuth configuration in SecurityConfiguration
 * 		5.	Login page (/login) and successhandler
 * 		6.	SuccessHandler will have some data. We can save data based on provider information.
*/		
//		FOR OAuth2 DEFAULT PROPERTY
//		httpSecurity.oauth2Login(Customizer.withDefaults());
		
		
		
		httpSecurity.oauth2Login(oauth2 ->
			oauth2.loginPage("/login")
			.successHandler(oAuthAuthenticationSuccessHandler)
		);
		
		return httpSecurity.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
