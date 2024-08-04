package com.contactmanager.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {

	public void removeVerificationMessageFromSession() {
		
		try {
			HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
			session.removeAttribute("message");
			session.removeAttribute("emailExists");
			session.removeAttribute("wrongPassword");
			session.removeAttribute("agreementappend");
			session.removeAttribute("user");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
