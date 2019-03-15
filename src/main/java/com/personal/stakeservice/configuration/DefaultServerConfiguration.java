package com.personal.stakeservice.configuration;

import com.personal.stakeservice.util.ApplicationProperties;

public class DefaultServerConfiguration implements IServerConfiguration {

	public static final int PORT = 8080;
	public static final String SERVER_PORT = "server.port";
	
	public static final String HOST = "localhost";
	public static final String SERVER_HOST = "server.host";
	
	@Override
	public int getServerPort() {
		String serverPort = ApplicationProperties.getProperty(SERVER_PORT);
		return serverPort == null? PORT : Integer.parseInt(serverPort);
	}


}
