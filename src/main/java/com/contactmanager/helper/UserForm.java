package com.contactmanager.helper;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserForm {

	@NotBlank(message = "Name is required.")
	@Size(min = 2, message = "Minimum 3 characters required.")
	private String name;
	
	@NotBlank(message = "Email is required.")
	@Email(message = "Invalid email address.")
	@Column(unique = true)
	private String email;
	
	@NotBlank(message = "Password is required.")
	@Size(min = 2, message = "Password length must be at least 6 letters.")
	private String password;
	
	@NotBlank(message = "Confirm Password is required.")
	private String confirmPassword;

	public UserForm() {
		super();
	}

	public UserForm(String name, String email, String password, String confirmPassword) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "UserForm [name=" + name + ", email=" + email + ", password=" + password + ", confirmPassword="
				+ confirmPassword + "]";
	}

}
