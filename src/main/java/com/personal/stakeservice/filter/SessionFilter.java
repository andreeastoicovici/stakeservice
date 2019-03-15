package com.personal.stakeservice.filter;

import java.io.IOException;
import java.net.URI;

import com.personal.stakeservice.controller.ResponseHelper;
import com.personal.stakeservice.model.Session;
import com.personal.stakeservice.repository.SessionRepository;
import com.personal.stakeservice.repository.factory.IRepositoryFactory;
import com.personal.stakeservice.repository.factory.RepositoryFactory;
import com.personal.stakeservice.server.HttpOperation;
import com.personal.stakeservice.server.HttpStatus;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class SessionFilter extends Filter {

	@Override
	public String description() {
		return null;
	}

	@Override
	public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
		if (exchange.getRequestMethod().equals(HttpOperation.POST)) {
			String sessionKey = getSessionKey(exchange.getRequestURI());
			if (sessionKey == null || sessionKey.isEmpty()) {
				ResponseHelper.createResponse(HttpStatus.BAD_REQUEST, "Session key must be provided.", exchange);
				return;
			}
			SessionRepository sessionRepository = RepositoryFactory.getSessionRepository();
			Session session = sessionRepository.find(sessionKey);
			if (session == null) {
				ResponseHelper.createResponse(HttpStatus.FORBIDDEN, "Session key is invalid.", exchange);
				return;
			}
			if (!session.isValid()) {
				ResponseHelper.createResponse(HttpStatus.FORBIDDEN, "Session key expired.", exchange);
				return;
			}
		}
		chain.doFilter(exchange);
	}

	private String getSessionKey(URI uri) {
		String query = uri.getRawQuery();
		if (query != null) {
			final String[] rawRequestParameters = query.split("[&;]", -1);
			for (String rawRequestParameter : rawRequestParameters) {
				String[] requestParameter = rawRequestParameter.split("=", 2);
				if (requestParameter.length > 0 && requestParameter[0].equals("sessionkey")) {
					return requestParameter[1];
				}
			}
		}
		return null;
	}

}
