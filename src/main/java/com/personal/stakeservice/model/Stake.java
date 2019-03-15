package com.personal.stakeservice.model;

public class Stake implements Comparable<Stake> {
	private int userId;
	private int stake;

	public Stake(int userId, int stake) {
		this.userId = userId;
		this.stake = stake;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getStake() {
		return stake;
	}

	public void setStake(int stake) {
		this.stake = stake;
	}

	@Override
	public int compareTo(Stake o) {
		return Integer.compare(stake, o.getStake());
	}
}