package com.personal.stakeservice.repository.factory;

import com.personal.stakeservice.repository.BetRepository;
import com.personal.stakeservice.repository.SessionRepository;
import com.personal.stakeservice.repository.UserRepository;

public interface IRepositoryFactory {
	
	BetRepository getBetRepository();
	SessionRepository getSessionRepository();
	UserRepository getUserRepository();

}
