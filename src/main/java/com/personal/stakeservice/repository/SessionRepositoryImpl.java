package com.personal.stakeservice.repository;

import java.util.concurrent.ConcurrentHashMap;

import com.personal.stakeservice.model.Session;

public class SessionRepositoryImpl implements SessionRepository {

	private ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
	
	@Override
	public void save(Session newSession) {
		sessions.compute(newSession.getSessionId(), (k, v) -> newSession);		
	}

	@Override
	public void delete(Session session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Session find(String sessionId) {
		return sessions.get(sessionId);
	}

	@Override
	public void deleteById(String sessionId) {
		sessions.remove(sessionId);
	}

	@Override
	public void deleteAllExpired() {
		sessions.forEach((key, value) -> {
			if (!value.isValid()) {
				sessions.remove(key);
			}
		});
	}

	@Override
	public void putIfAbsent(Session newSession) {
		sessions.computeIfAbsent(newSession.getSessionId(), (k) -> newSession);
	}

	@Override
	public long size() {
		return sessions.size();
	}

}
