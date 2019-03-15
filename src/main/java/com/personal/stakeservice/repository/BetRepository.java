package com.personal.stakeservice.repository;

import java.util.List;

import com.personal.stakeservice.model.Stake;

public interface BetRepository extends IRepository {
	
	void save(int betOfferId, Stake newStake);
	
	List<Stake> findHighestStakes(int betOfferId, int topCount);

}
