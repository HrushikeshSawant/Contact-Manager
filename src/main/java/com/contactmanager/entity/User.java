package com.contactmanager.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@Column(nullable = false)
	private String name;
	@Column(unique = true, nullable = false)
	private String email;
	private String password;
	@Transient
	private String confirmPassword;
	private String profilePic;
	private String phone;
	private String role;

	// TO VERIFY USER EMAIL AND NUMBER
	private boolean status = false;
	private boolean emailVerified = false;
	private boolean numberVerified = false;

	// TO MANAGE FROM WHICH USER HAS SIGNED UP. DEFAULT=SELF
	private Providers provider = Providers.SELF;
	private String providerUserId;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Contact> contacts = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Email> sentEmail = new ArrayList<>();

	public User() {
		super();
	}

	public User(int userId, String name, String email, String password, String confirmPassword, String profilePic,
			String phone, String role, boolean status, boolean emailVerified, boolean numberVerified,
			Providers provider, String providerUserId, List<Contact> contacts, List<Email> sentEmail) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.profilePic = profilePic;
		this.phone = phone;
		this.role = role;
		this.status = status;
		this.emailVerified = emailVerified;
		this.numberVerified = numberVerified;
		this.provider = provider;
		this.providerUserId = providerUserId;
		this.contacts = contacts;
		this.sentEmail = sentEmail;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public boolean isNumberVerified() {
		return numberVerified;
	}

	public void setNumberVerified(boolean numberVerified) {
		this.numberVerified = numberVerified;
	}

	public Providers getProvider() {
		return provider;
	}

	public void setProvider(Providers provider) {
		this.provider = provider;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public List<Email> getSentEmail() {
		return sentEmail;
	}

	public void setSentEmail(List<Email> sentEmail) {
		this.sentEmail = sentEmail;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", profilePic=" + profilePic + ", phone=" + phone + ", role="
				+ role + ", status=" + status + ", emailVerified=" + emailVerified + ", numberVerified="
				+ numberVerified + ", provider=" + provider + ", providerUserId=" + providerUserId + ", contacts="
				+ contacts + ", sentEmail=" + sentEmail + "]";
	}

}
