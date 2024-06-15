package com.contactmanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Email {

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String userEmail;
	private String contactEmail;
	@Column(length = 500)
	private String content;

	@ManyToOne
	private User user;

	public Email() {
		super();
	}

	public Email(int id, String userEmail, String contactEmail, String content, User user) {
		super();
		this.id = id;
		this.userEmail = userEmail;
		this.contactEmail = contactEmail;
		this.content = content;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
