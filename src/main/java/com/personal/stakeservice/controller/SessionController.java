package com.personal.stakeservice.controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.personal.controller.stakeservice.annotations.RequestMapping;
import com.personal.stakeservice.model.Session;
import com.personal.stakeservice.server.HttpStatus;
import com.personal.stakeservice.service.SessionService;
import com.personal.stakeservice.service.UserService;
import com.sun.net.httpserver.HttpExchange;

public class SessionController {

	private static final Logger LOG = LoggerFactory.getLogger(SessionController.class);

	private SessionService sessionService = new SessionService();

	private UserService userService = new UserService();

	@RequestMapping(path = "/(\\d+)/session$", method = "GET")
	public String getSession(HttpExchange exchange) throws IOException {
		Matcher matcher = Pattern.compile("/(\\d+)/session$").matcher(exchange.getRequestURI().getPath());
		Session session = null;
		int userId = -1;
		if (matcher.find()) {
			userId = Integer.parseInt(matcher.group(1));
			userService.createUser(userId);
			session = sessionService.createSession(userId);
			ResponseHelper.createResponse(HttpStatus.OK, session.getSessionId(), exchange);
		}
		return session.getSessionId();
	}

}
