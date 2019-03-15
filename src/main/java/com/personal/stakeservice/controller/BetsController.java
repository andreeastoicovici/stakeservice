package com.personal.stakeservice.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.personal.stakeservice.controller.annotations.RequestMapping;
import com.personal.stakeservice.server.HttpStatus;
import com.personal.stakeservice.service.BetService;
import com.personal.stakeservice.service.SessionService;
import com.sun.net.httpserver.HttpExchange;

public class BetsController {

	private BetService betsService = new BetService();

	private SessionService sessionService = new SessionService();

	@RequestMapping(method = "POST", path = "/(\\d+)/stake\\?sessionkey=([a-fA-F0-9-]+)")
	public void addStake(HttpExchange exchange) throws IOException {
		Matcher matcher = Pattern.compile("/(\\d+)/stake\\?sessionkey=([a-fA-F0-9-]+)$")
				.matcher(exchange.getRequestURI().getRawSchemeSpecificPart());
		String value = null;
		if (matcher.find()) {
			String sessionKey = matcher.group(2);
			int betOfferId = Integer.parseInt(matcher.group(1));
			try (BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "utf-8"))) {
				value = br.readLine();
				if (value == null) {
					ResponseHelper.createResponse(HttpStatus.BAD_REQUEST, "Stake value not provided", exchange);
				} else {
					try {
						Integer.parseInt(value);
					} catch(NumberFormatException e) {
						ResponseHelper.createResponse(HttpStatus.BAD_REQUEST, "Stake value not provided", exchange);
					}
					int userId = sessionService.getSession(sessionKey).get().getUserId();
					betsService.addStake(betOfferId, Integer.parseInt(value), userId);
					ResponseHelper.createResponse(HttpStatus.OK, "", exchange);
				}
			} 
		}
		if (value == null) {
			ResponseHelper.createResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.", exchange);
		}
	}

	@RequestMapping(method = "GET", path = "/(\\d+)/highstakes")
	public void getHighestStakes(HttpExchange exchange) throws IOException {
		Matcher matcher = Pattern.compile("/(\\d+)/highstakes$").matcher(exchange.getRequestURI().getPath());
		if (matcher.find()) {
			int betOfferId = Integer.parseInt(matcher.group(1));
			String highestStakes = betsService.getHighStakes(betOfferId, 20);
			ResponseHelper.createResponse(HttpStatus.OK, highestStakes, exchange);
		}
	}
}
