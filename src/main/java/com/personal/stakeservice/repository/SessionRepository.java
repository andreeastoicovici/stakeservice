package com.personal.stakeservice.repository;

import com.personal.stakeservice.model.Session;

public interface SessionRepository extends IRepository {
	
	void save(Session newSession);
	
	void putIfAbsent(Session newSession);
	
	void delete(Session session);
	
	Session find(String sessionId);

	void deleteById(String sessionId);
	
	void deleteAllExpired();
}
