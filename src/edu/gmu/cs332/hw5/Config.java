package edu.gmu.cs332.hw5;

import java.util.*;

/**
 * @author session
 */
public class Config {
	private Properties properties;
	private Config cfg;
	
	// Keep the constructor private so the only interface is through getInstance()
	private Config() {
		properties = new Properties();
	}
	
	public Config getInstance() {
		if (cfg == null) {
			cfg = new Config();
		}
		
		return cfg;
	}
	
	public String get(String key) {
		return (String)properties.get(key);
	}
	
	public void set(String key, String value) {
		properties.put(key, value);
	}
}
