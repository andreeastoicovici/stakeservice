package com.personal.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.personal.stakeservice.model.Session;
import com.personal.stakeservice.server.HttpStatus;
import com.personal.stakeservice.server.SimpleHttpServer;
import com.personal.stakeservice.service.BetService;
import com.personal.stakeservice.service.SessionService;
import com.personal.stakeservice.service.UserService;
import com.personal.test.util.TestServerConfiguration;
import com.personal.test.util.TestUtil;

public class SimpleHttpServerTest {

	private static SimpleHttpServer server = new SimpleHttpServer(new TestServerConfiguration());

	@BeforeClass
	public static void initialSetUp() throws Exception {
		server.start();
	}

	@AfterClass
	public static void tearDown() {
		server.stop();
	}

	@Test
	public void whenGettingSessionKeyExpectSuccess() throws ClientProtocolException, IOException {
		HttpResponse httpResponse = TestUtil.executeGetRequest("/1234/session", null);
		assertTrue(httpResponse.getStatusLine().getStatusCode() == 200);
		String body = EntityUtils.toString(httpResponse.getEntity());
		assertTrue(body != null && body.length() > 0);
	}

	@Test
	public void whenPostingStakeWithValidSessionKeyExpectSuccess() throws ClientProtocolException, IOException {
		UserService userService = new UserService();
		int userId = 9876;
		userService.createUser(9876);
		SessionService sessionService = new SessionService();
		Session session = sessionService.createSession(userId);
		
		HttpResponse httpResponse = TestUtil.executePostRequest("/888/stake?sessionkey=" + session.getSessionId(), "4500", null);
		assertTrue(httpResponse.getStatusLine().getStatusCode() == HttpStatus.OK);
	}
	
	@Test
	public void whenPostingStakeWithExpiredSessionKeyExpectForbidden() throws ClientProtocolException, IOException {
		UserService userService = new UserService();
		int userId = 9876;
		userService.createUser(9876);
		SessionService sessionService = new SessionService();
		Session session = sessionService.createSession(userId);
		long creationTime = session.getCreationTime();
		session.setCreationTime(creationTime - 5*60*1000);
		
		HttpResponse httpResponse = TestUtil.executePostRequest("/888/stake?sessionkey=" + session.getSessionId(), "4500", null);
		assertTrue(httpResponse.getStatusLine().getStatusCode() == HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void whenPostingStakeWithMissingSessionKeyExpectBadRequest() throws ClientProtocolException, IOException {
		HttpResponse httpResponse = TestUtil.executePostRequest("/888/stake", "4500", null);
		assertTrue(httpResponse.getStatusLine().getStatusCode() == HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void whenGettingHighestStakesSuccess() throws ClientProtocolException, IOException {
		Map<Integer, String> userToSessionMap = create1000Users();
		create1000Stakes(888, userToSessionMap.keySet().stream().collect(Collectors.toList()));
		HttpResponse httpResponse = TestUtil.executeGetRequest("/888/highstakes", null);
		assertTrue(httpResponse.getStatusLine().getStatusCode() == HttpStatus.OK);
		
		String body = EntityUtils.toString(httpResponse.getEntity());
		assertEquals(20, body.split(",").length);
	}
	
	private Map<Integer, String> create1000Users() {
		Map<Integer, String> userToSessionMap = new HashMap<>();
		for (int i = 1000; i < 2000; i++) {
			UserService userService = new UserService();
			userService.createUser(i);
			SessionService sessionService = new SessionService();
			Session session = sessionService.createSession(i);
			userToSessionMap.put(i, session.getSessionId());
		}
		return userToSessionMap;
	}
	
	private void create1000Stakes(int betOfferId, List<Integer> userIds) {
		BetService betService = new BetService();
		for (int i = 0; i < 1000; i++) {
			betService.addStake(betOfferId, ThreadLocalRandom.current().nextInt(1, 1000),
					userIds.get(ThreadLocalRandom.current().nextInt(0, userIds.size())));
		}
	}

}
