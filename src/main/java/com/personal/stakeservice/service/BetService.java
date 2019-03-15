package com.personal.stakeservice.service;

import java.util.List;

import com.personal.stakeservice.model.Stake;
import com.personal.stakeservice.repository.BetRepository;
import com.personal.stakeservice.repository.factory.RepositoryFactory;

public final class BetService {
	
	private BetRepository betRepository = RepositoryFactory.getBetRepository();
	
	public void addStake(int betOffer, int stake, int userId) {
		Stake newStake = new Stake(userId, stake);
		betRepository.save(betOffer, newStake);
	}
	
	public String getHighStakes(int betOffer, int topCount) {
		List<Stake> highestStakes = betRepository.findHighestStakes(betOffer, topCount);
		StringBuilder sb = new StringBuilder();
		for (Stake stake : highestStakes) {
			sb.append(stake.getUserId() + "=" + stake.getStake() + ",");
		}
		String value = sb.toString();
		if (value.endsWith(",")) {
			return value.substring(0, value.length());
		}
		return value;
	}

}
