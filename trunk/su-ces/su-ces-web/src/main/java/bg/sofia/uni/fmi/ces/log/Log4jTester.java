package bg.sofia.uni.fmi.ces.log;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Log4jTester {
	
	private static final Logger logger = LogManager.getLogger(Log4jTester.class);
	
	public static void main(String[] args) {
		new Log4jTester().executeLog();
	}
	
	public void executeLog() {
		logger.debug("Test: debug msg");
		logger.error("Test: error msg");
		logger.warn("Test: warning msg");
		logger.info("Test: info msg");
		logger.fatal("Test: fatal msg");
	}
	
	public static void logFromJsp() {
		new Log4jTester().executeLog();
	}

}
