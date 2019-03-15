package com.personal.stakeservice.model;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Session {

	private String sessionId;
	private long creationTime;
	private int validity;
	private int userId;
	
	/**
	 * 
	 * @param sessionId
	 * @param validity The amount of minutes this session is considered valid
	 */
	public Session(String sessionId, int validity) {
		this.sessionId = sessionId;
		creationTime = new Date().getTime();
		this.validity = validity;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}
	
	/**
	 * 
	 * @return whether this session is still valid, meaning it did not exceed the
	 *         validity field value.
	 */
	public boolean isValid() {
		long timeNow =  new Date().getTime();
		long minutes = TimeUnit.MILLISECONDS.toMinutes(timeNow - creationTime);
		return minutes < validity;
	}

	public int getValidity() {
		return validity;
	}

	public void setValidity(int validity) {
		this.validity = validity;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
