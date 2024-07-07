package com.contactmanager.serviceImplementation;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.contactmanager.entity.User;
import com.contactmanager.exception.ResourceNotFoundException;
import com.contactmanager.helper.ApplicationConstants;
import com.contactmanager.repository.UserRepository;
import com.contactmanager.service.UserService;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public User registerUser(User user) {
		log.debug(user.toString());
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoleList(List.of(ApplicationConstants.ROLE_USER));
		
		return userRepository.save(user);
	}

	@Override
	public Optional<User> findUserById(int id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<User> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Optional<User> updateUser(User user) {
		User fetchedUser = userRepository.findById(user.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found!!"));

		// UPDATING DB USER DETAILS WITH NEW USER DETAILS
		fetchedUser.setName(user.getName());
		fetchedUser.setEmail(user.getEmail());
		fetchedUser.setPassword(user.getPassword());
		fetchedUser.setProfilePic(user.getProfilePic());
		fetchedUser.setPhone(user.getPhone());
		fetchedUser.setEnabled(user.isEnabled());
		fetchedUser.setEmailVerified(user.isEmailVerified());
		fetchedUser.setphoneVerified(user.isphoneVerified());
		fetchedUser.setProvider(user.getProvider());
		fetchedUser.setProviderUserId(user.getProviderUserId());

		// SAVING UPDATED USER
		User save = userRepository.save(fetchedUser);

		return Optional.ofNullable(save);
	}

	@Override
	public void deleteUser(int id) {
		User fetchedUser = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found!!"));
		userRepository.delete(fetchedUser);
	}

	@Override
	public boolean isUserExistsById(int id) {
		User fetchedUser = userRepository.findById(id).orElse(null);
		return fetchedUser != null ? true : false;
	}

	@Override
	public boolean isUserExistsByEmail(String email) {
		User fetchedUser = userRepository.findByEmail(email).orElse(null);
		return fetchedUser != null ? true : false;
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

}
