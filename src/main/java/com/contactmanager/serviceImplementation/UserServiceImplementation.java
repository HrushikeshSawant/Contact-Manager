package com.contactmanager.serviceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contactmanager.entity.User;
import com.contactmanager.repository.UserRepository;
import com.contactmanager.service.UserService;


@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public String register(User user) {

		User result = userRepository.save(user);
		System.out.println(result);
		return null;
	}

}
