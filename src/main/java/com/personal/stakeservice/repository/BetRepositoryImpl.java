package com.personal.stakeservice.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import com.personal.stakeservice.model.Stake;

public class BetRepositoryImpl implements BetRepository {
	
	private Map<Integer, TreeMap<Stake, Stake>> betOffers = new ConcurrentHashMap<>();

	@Override
	public void save(int bidOfferId, Stake newStake) {
		betOffers.compute(bidOfferId, (key, value) -> {
			if (value == null) {
				value = new TreeMap<>();
			}
			value.put(newStake, newStake);
			return value;
		});
	}

	@Override
	public List<Stake> findHighestStakes(int betOffer, int topCount) {
		List<Stake> stakes = new ArrayList<Stake>();
		Set<Integer> usersProcessed = new HashSet<>();
		for (Entry<Stake,Stake> stake : betOffers.get(betOffer).entrySet()) {
			Stake value = stake.getValue();
			int userId = value.getUserId();
			if (!usersProcessed.contains(userId)) {
				stakes.add(value);
				usersProcessed.add(userId);
			}
			if (usersProcessed.size() >= topCount) {
				break;
			}
		}
		return stakes;
	}

	@Override
	public long size() {
		return betOffers.size();
	}
	
}
