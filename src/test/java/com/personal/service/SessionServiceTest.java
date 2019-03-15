package com.personal.service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.personal.stakeservice.repository.SessionRepository;
import com.personal.stakeservice.repository.factory.RepositoryFactory;
import com.personal.stakeservice.service.SessionService;
import com.personal.test.util.TestUtil;

public class SessionServiceTest {
	
	@Test
	public void create10000InParallelSessionsExpectSuccess() throws InterruptedException {
		SessionRepository sessionRepository = RepositoryFactory.getSessionRepository();
		long expectedSize = sessionRepository.size() + 10000;
		
        ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(10);
        threadPoolExecutor.prestartAllCoreThreads();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        List<Integer> randomUserIds = TestUtil.generateIntegerIds(10000);
        final SessionService sessionService = new SessionService();
        
        randomUserIds.parallelStream().forEach(
                userId -> threadPoolExecutor.submit(
                                (Callable<Void>) () -> {
                                    countDownLatch.await();
                                    sessionService.createSession(userId);
                                    return null;
                                }
                        )
        );
        countDownLatch.countDown();
        threadPoolExecutor.shutdown();
        threadPoolExecutor.awaitTermination(20L, TimeUnit.SECONDS);
        
        Assert.assertTrue(sessionRepository.size() == expectedSize);
	}
	
}
