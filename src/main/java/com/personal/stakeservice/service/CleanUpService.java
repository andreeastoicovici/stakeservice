package com.personal.stakeservice.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.personal.stakeservice.repository.SessionRepository;

public class CleanUpService {

	private static final Logger LOG = LoggerFactory.getLogger(CleanUpService.class);

	private SessionRepository sessionRepository;

	private ScheduledExecutorService scheduledExecutor;

	public CleanUpService(SessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}

	public void start() {
		if (scheduledExecutor != null && scheduledExecutor.isShutdown()) {
			scheduledExecutor.shutdownNow();
			LOG.info("Shutting down cleaning up scheduler.");
		}
		scheduledExecutor = Executors.newScheduledThreadPool(1, new DaemonThreadFactory());
		scheduledExecutor.scheduleAtFixedRate(() -> sessionRepository.deleteAllExpired(), 15, 30, TimeUnit.MINUTES);
		LOG.info("Started cleaning up sessions scheduler.");
	}

	public void stop() {
		if (scheduledExecutor != null && !scheduledExecutor.isShutdown() && !scheduledExecutor.isTerminated()) {
			scheduledExecutor.shutdownNow();
			LOG.info("Stopped cleaning up sessions scheduler.");
		}
	}

}
