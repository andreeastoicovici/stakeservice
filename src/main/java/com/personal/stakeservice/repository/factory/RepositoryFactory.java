package com.personal.stakeservice.repository.factory;

import com.personal.stakeservice.repository.BetRepository;
import com.personal.stakeservice.repository.SessionRepository;
import com.personal.stakeservice.repository.UserRepository;

public final class RepositoryFactory {
	
	private static IRepositoryFactory registeredRepositoryFactory;
	
	static {
		registeredRepositoryFactory = new DefaultRepositoryFactory();
	}
	
	public static void registerRepositoryFactory(IRepositoryFactory repositoryFactory) {
		registeredRepositoryFactory = repositoryFactory;
	}
	
	public static BetRepository getBetRepository() {
		return registeredRepositoryFactory.getBetRepository();
	}
	
	public static SessionRepository getSessionRepository() {
		return registeredRepositoryFactory.getSessionRepository();
	}

	public static UserRepository getUserRepository() {
		return registeredRepositoryFactory.getUserRepository();
	}
	
}
