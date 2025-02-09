package com.contactmanager.service.implementation;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contactmanager.entity.User;
import com.contactmanager.exception.ResourceNotFoundException;
import com.contactmanager.repository.UserRepository;
import com.contactmanager.service.UserService;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public User registerUser(User user) {
		log.debug(user.toString());
		return userRepository.save(user);
	}

	@Override
	public User findUserById(int id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
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
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
		userRepository.delete(fetchedUser);
	}

	@Override
	public boolean isUserExistsById(int id) {
		User fetchedUser = userRepository.findById(id).orElse(null);
		return fetchedUser != null;	//RETURN TRUE IF USER EXISTS ELSE FALSE 
	}

	@Override
	public boolean isUserExistsByEmail(String email) {
		User fetchedUser = userRepository.findByEmail(email).orElse(null);
		return fetchedUser != null;	//RETURN TRUE IF USER EXISTS ELSE FALSE
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

}
