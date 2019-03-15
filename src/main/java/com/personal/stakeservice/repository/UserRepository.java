package com.personal.stakeservice.repository;

import com.personal.stakeservice.model.User;

public interface UserRepository extends IRepository {
	
	void save(User user);
	
	User findById(int userId);
	
	void putIfAbsent(User user);

}
