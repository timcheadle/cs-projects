package edu.gmu.cs332.hw5;

import java.util.*;
import java.io.*;

/**
 * Config is a singleton class that stores configuration information in key/value
 * string pairs.  Configuration can either be set each property at a time or loaded
 * all at once via an input stream.
 * 
 * @author Tim Cheadle
 */
public class Config {
	private Properties properties;
	private static Config cfg;
	
	// Keep the constructor private so the only interface is through getInstance()
	private Config() {
		properties = new Properties();
	}
	
	/**
	 * The common interface through which to access the singleton Config object.  This
	 * method will create a Config object the first time it's called and refer to that
	 * same object any other time its called.
	 * 
	 * @return The singleton instance
	 */
	public static Config getInstance() {
		if (cfg == null) {
			cfg = new Config();
		}
		
		return cfg;
	}
	
	/**
	 * Loads a set of properties from an input stream.
	 * 
	 * @param input The input stream containing key/value pairs
	 * @throws IOException if an error occurred while reading from the input stream
	 */
	public void load(InputStream input) throws IOException {
		properties.load(input);
	}
	
	/**
	 * Get the value of a given property
	 * @param key The property to return
	 * @return The value of the property
	 */
	public String get(String key) {
		return properties.getProperty(key);
	}
	
	/**
	 * Set the value of a given property
	 * 
	 * @param key The property to set
	 * @param value The value to set
	 */
	public void set(String key, String value) {
		properties.setProperty(key, value);
	}
}
