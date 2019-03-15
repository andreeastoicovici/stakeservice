package com.personal.stakeservice.service;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.personal.stakeservice.model.Session;
import com.personal.stakeservice.repository.SessionRepository;
import com.personal.stakeservice.repository.factory.RepositoryFactory;
import com.personal.stakeservice.util.ApplicationProperties;

public final class SessionService {
	
	private static final String SESSION_EXPIRATION = "session.expiration";
	
	private static String expiration = ApplicationProperties.getProperty(SESSION_EXPIRATION);

	private static final Logger LOG = LoggerFactory.getLogger(SessionService.class);
	
	private SessionRepository sessionRepository = RepositoryFactory.getSessionRepository();
	
	public Session createSession(int userId) {
		String newSessionId = UUID.randomUUID().toString();
		int expirationTimeConfiguration = getExpirationPolicy();
		while (sessionRepository.find(newSessionId) != null) {
			newSessionId = UUID.randomUUID().toString();
		}
        Session session = new Session(newSessionId, expirationTimeConfiguration);
		session.setUserId(userId);
		sessionRepository.save(session);
		return session;
	}

	private int getExpirationPolicy() {
		String sessionExpiration = expiration;
		return sessionExpiration == null? 10 : Integer.parseInt(sessionExpiration);
	}
	
	public void deleteSession(String sessionId) {
		sessionRepository.deleteById(sessionId);
	}
	
	public Optional<Session> getSession(String sessionId) {
		Session session = sessionRepository.find(sessionId);
		return session != null ? Optional.of(session) : Optional.empty();
	}
	
}
