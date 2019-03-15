package com.personal.stakeservice.server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.personal.stakeservice.controller.ResponseHelper;
import com.personal.stakeservice.util.AnnotationsParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class StakesHttpHandler implements HttpHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(StakesHttpHandler.class);
	
	private Map<String, List<Handler>> mappedHandlers;
	
	public StakesHttpHandler() throws Exception {
		mappedHandlers = AnnotationsParser.getMappedHandlers("com.personal.stakeservice.controller");
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String path = exchange.getRequestURI().getRawSchemeSpecificPart();
		List<Handler> handlers = new ArrayList<Handler>();
		for (String pathPattern : mappedHandlers.keySet()) {
			if (Pattern.matches(pathPattern, path)) {
				handlers = mappedHandlers.get(pathPattern);
			}
		}
		if (handlers.size() == 0) {
			ResponseHelper.createResponse(HttpStatus.PAGE_NOT_FOUND, "Page not found.", exchange);
		}
		// Can have multiple operations for the same path
		for (Handler handler : handlers) {
			if (handler.getHttpOperation().equals(exchange.getRequestMethod())) {
				try {
					handler.getMethod().invoke(handler.getInstance(), exchange);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					ResponseHelper.createResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to execute operation", exchange);
				}
			}
		}
	}

}
