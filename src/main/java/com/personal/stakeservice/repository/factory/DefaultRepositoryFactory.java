package com.personal.stakeservice.repository.factory;

import com.personal.stakeservice.repository.BetRepository;
import com.personal.stakeservice.repository.BetRepositoryImpl;
import com.personal.stakeservice.repository.SessionRepository;
import com.personal.stakeservice.repository.SessionRepositoryImpl;
import com.personal.stakeservice.repository.UserRepository;
import com.personal.stakeservice.repository.UserRepositoryImpl;

public class DefaultRepositoryFactory implements IRepositoryFactory {

	private BetRepository betRepository;
	private SessionRepository sessionRepository;
	private UserRepository userRepository;
	
	public DefaultRepositoryFactory() {
		betRepository = new BetRepositoryImpl();
		sessionRepository = new SessionRepositoryImpl();
		userRepository = new UserRepositoryImpl();
	}
	
	@Override
	public BetRepository getBetRepository() {
		return betRepository;
	}

	@Override
	public SessionRepository getSessionRepository() {
		return sessionRepository;
	}

	@Override
	public UserRepository getUserRepository() {
		return userRepository;
	}
	
}
