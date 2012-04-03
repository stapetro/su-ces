package bg.sofia.uni.fmi.ces.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class SucesConfigurator {
	
	private Logger logger;
	private Properties properties;
	private static SucesConfigurator sucesConfigurator;
	
	public static String getValue(String key) {
		if(sucesConfigurator == null) {
			sucesConfigurator = new SucesConfigurator();
		}
		return sucesConfigurator.getPropValue(key);
	}
	
	private SucesConfigurator() {
		ClassLoader classLoader = this.getClass().getClassLoader();
		final String CONFIG_FILE = "conf/sucesweb.properties";
		InputStream confInputStream = classLoader.getResourceAsStream(CONFIG_FILE);
		this.properties = new Properties();
		try {
			this.properties.load(confInputStream);
		} catch (IOException e) {
			getLogger().fatal("Sucesweb config file '" + CONFIG_FILE + "' not found");
		}
	}
	
	private String getPropValue(String key) {
		if(this.properties == null) {
			return "";
		}
		if(key == null || key.isEmpty()) {
			getLogger().error("Key is NULL or empty");
			return "";
		}
		String value = this.properties.getProperty(key);
		if(value == null || value.isEmpty()) {
			getLogger().error("Value for key '" + key + "' not found");
			return "";
		}
		return value;
	}
	
	private Logger getLogger() {
		if(this.logger == null) {
			this.logger = LogManager.getLogger(SucesConfigurator.class);
		}
		return this.logger;
	}
}
