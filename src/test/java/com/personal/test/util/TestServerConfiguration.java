package com.personal.test.util;

import com.personal.stakeservice.configuration.IServerConfiguration;

public class TestServerConfiguration implements IServerConfiguration {

	@Override
	public int getServerPort() {
		return 8888;
	}
	
}
