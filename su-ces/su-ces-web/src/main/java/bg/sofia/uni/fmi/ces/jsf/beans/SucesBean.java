package bg.sofia.uni.fmi.ces.jsf.beans;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Represents common managed bean functionality.
 * 
 * @author Staskata
 * 
 */
public abstract class SucesBean {

	private transient Logger logger;

	protected Logger getLogger() {
		if (logger == null) {
			logger = LogManager.getLogger(this.getClass());
		}
		return logger;
	}

}
