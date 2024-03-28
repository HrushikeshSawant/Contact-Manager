package com.contactmanager.service;

import org.springframework.stereotype.Service;

import com.contactmanager.entity.User;

@Service
public interface UserService {

	String register(User user);
	
}
