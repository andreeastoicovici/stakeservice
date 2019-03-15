package com.personal.stakeservice.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.personal.stakeservice.model.User;

public class UserRepositoryImpl implements UserRepository {

	private Map<Integer, User> users = new ConcurrentHashMap<>();
	
	public void save(User user) {
		users.putIfAbsent(user.getUserId(), user);
	}
	
	public User findById(int userId) {
		return users.get(userId);
	}

	@Override
	public void putIfAbsent(User user) {
		users.putIfAbsent(user.getUserId(), user);
	}

	@Override
	public long size() {
		return users.size();
	}
}
