package com.personal.stakeservice.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationProperties {
	
	private static final Logger LOG = LoggerFactory.getLogger(ApplicationProperties.class);
	
	private static final String APPLICATION_PROPERTIES = "application.properties";
	
	public static String getProperty(String key) {
		Properties properties = new Properties();
		try(InputStream is = ApplicationProperties.class.getClassLoader().getResourceAsStream(APPLICATION_PROPERTIES)) {
			properties.load(is);
	        return properties.getProperty(key);
		} catch (IOException | NumberFormatException e) {
			LOG.debug("Failed to load application.properties file.", e);
		}
		return null;
	}

}
