package com.personal.stakeservice.service;

import java.util.concurrent.ThreadFactory;

class DaemonThreadFactory implements ThreadFactory {
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r);
		thread.setDaemon(true);
		return thread;
	}
}