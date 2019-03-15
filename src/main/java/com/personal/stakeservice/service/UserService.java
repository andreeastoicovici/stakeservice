package com.personal.stakeservice.service;

import com.personal.stakeservice.model.User;
import com.personal.stakeservice.repository.UserRepository;
import com.personal.stakeservice.repository.factory.RepositoryFactory;

public class UserService {
	
	private UserRepository userRepository = RepositoryFactory.getUserRepository();
	
	public User createUser(int userId) {
		User user = new User(userId);
		userRepository.putIfAbsent(user);
		return user;
	}
	
	public User getUser(int userId) {
		return userRepository.findById(userId);
	}
	
	public void updateUser(User user) {
		userRepository.save(user);
	}
	
}
