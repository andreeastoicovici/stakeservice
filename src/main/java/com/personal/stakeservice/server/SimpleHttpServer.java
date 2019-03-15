package com.personal.stakeservice.server;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.personal.stakeservice.configuration.DefaultServerConfiguration;
import com.personal.stakeservice.configuration.IServerConfiguration;
import com.personal.stakeservice.filter.SessionFilter;
import com.personal.stakeservice.repository.factory.DefaultRepositoryFactory;
import com.personal.stakeservice.repository.factory.RepositoryFactory;
import com.sun.net.httpserver.HttpServer;

public class SimpleHttpServer {
	
	private static final Logger LOG = LoggerFactory.getLogger(SimpleHttpServer.class);
	
	private IServerConfiguration serverConfiguration;
	
	private HttpServer server;
	
	public SimpleHttpServer(IServerConfiguration serverConfiguration) {
		this.serverConfiguration = serverConfiguration;
	}
    
    public void start() throws Exception {
    	RepositoryFactory.registerRepositoryFactory(new DefaultRepositoryFactory());
    	server = HttpServer.create(new InetSocketAddress(serverConfiguration.getServerPort()), 1);
    	createContext(server);
        server.start();
    }
    
    public void stop() {
    	server.stop(0);
    }
    
    public static void main(final String... args) throws Exception {
    	new SimpleHttpServer(new DefaultServerConfiguration()).start();
    }
    
    private void createContext(HttpServer httpServer) throws Exception {
    	httpServer.createContext("/", new StakesHttpHandler()).getFilters().add(new SessionFilter());
    }

}
