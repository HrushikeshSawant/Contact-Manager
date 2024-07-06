package com.contactmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.contactmanager.entity.User;

@Service
public interface UserService {

	User registerUser(User user);
	
	Optional<User> findUserById(int id);
	
	Optional<User> findUserByEmail(String email);
	
	Optional<User> updateUser(User user);
	
	void deleteUser(int id);
	
	boolean isUserExistsById(int id);
	
	boolean isUserExistsByEmail(String email);
	
	List<User> findAllUsers();
	
}
