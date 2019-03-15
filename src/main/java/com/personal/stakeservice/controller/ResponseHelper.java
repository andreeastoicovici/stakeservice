package com.personal.stakeservice.controller;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

public final class ResponseHelper {
	
	public static void createResponse(int status, String message, HttpExchange exchange) throws IOException {
		if (message == null) {
			exchange.sendResponseHeaders(status, 0);
		} else {
			exchange.sendResponseHeaders(status, message.length());
	        OutputStream os = exchange.getResponseBody();
	        os.write(message.getBytes("UTF-8"));
		}
        exchange.close();
    }

}
