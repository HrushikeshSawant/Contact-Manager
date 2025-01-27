package com.contactmanager.helper;

import java.util.ArrayList;
import java.util.List;

import com.contactmanager.entity.Contact;
import com.contactmanager.entity.Email;

public class LoggedInUserData {

	private String name;
	private String email;
	private String profilePic;
	private String phone;
	private boolean enabled = true;
	private boolean emailVerified = false;
	private boolean phoneVerified = false;
	private List<Contact> contacts = new ArrayList<>();
	private List<Email> sentEmail = new ArrayList<>();

	public LoggedInUserData() {
		super();
	}

	public LoggedInUserData(String name, String email, String profilePic, String phone, boolean enabled,
			boolean emailVerified, boolean phoneVerified, List<Contact> contacts, List<Email> sentEmail) {
		super();
		this.name = name;
		this.email = email;
		this.profilePic = profilePic;
		this.phone = phone;
		this.enabled = enabled;
		this.emailVerified = emailVerified;
		this.phoneVerified = phoneVerified;
		this.contacts = contacts;
		this.sentEmail = sentEmail;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public String getPhone() {
		return phone;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public boolean isPhoneVerified() {
		return phoneVerified;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public List<Email> getSentEmail() {
		return sentEmail;
	}

}
