package com.contactmanager.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.contactmanager.helper.Providers;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@Column(nullable = false)
	private String name;
	@Column(unique = true, nullable = false)
	private String email;
	private String password;
	private String profilePic;
	private String phone;

	// TO VERIFY USER EMAIL AND NUMBER
	private boolean enabled = true;
	private boolean emailVerified = false;
	private boolean phoneVerified = false;

	// TO MANAGE FROM WHICH USER HAS SIGNED UP. DEFAULT=SELF
	@Enumerated(EnumType.STRING)
	private Providers provider = Providers.SELF;
	private String providerUserId;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Contact> contacts = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Email> sentEmail = new ArrayList<>();
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roleList = new ArrayList<>();

	public User() {
		super();
	}

	public User(int userId, String name, String email, String password, String profilePic,
			String phone, boolean enabled, boolean emailVerified, boolean phoneVerified,
			Providers provider, String providerUserId, List<Contact> contacts, List<Email> sentEmail) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.profilePic = profilePic;
		this.phone = phone;
		this.enabled = enabled;
		this.emailVerified = emailVerified;
		this.phoneVerified = phoneVerified;
		this.provider = provider;
		this.providerUserId = providerUserId;
		this.contacts = contacts;
		this.sentEmail = sentEmail;
	}

	public boolean isPhoneVerified() {
		return phoneVerified;
	}

	public void setPhoneVerified(boolean phoneVerified) {
		this.phoneVerified = phoneVerified;
	}

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public boolean isphoneVerified() {
		return phoneVerified;
	}

	public void setphoneVerified(boolean phoneVerified) {
		this.phoneVerified = phoneVerified;
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
				+ ", profilePic=" + profilePic + ", phone=" + phone + ", enabled=" + enabled + ", emailVerified=" + emailVerified + ", phoneVerified="
				+ phoneVerified + ", provider=" + provider + ", providerUserId=" + providerUserId + ", contacts="
				+ contacts + ", sentEmail=" + sentEmail + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		//CONVERTING LIST OF ROLES [USER, ADMIN] TO LIST OF SimpleGrantedAuthority [roles(USER, ADMIN}]
		
		List<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
		return roles;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

}
