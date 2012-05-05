package bg.sofia.uni.fmi.ces.utils.session;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import bg.sofia.uni.fmi.ces.constants.BeanNames;
import bg.sofia.uni.fmi.ces.jsf.beans.SessionBean;

/**
 * This class provided a basic mechanism for accessing and working with session
 * objects.
 */
public class SessionUtils {

	/**
	 * Provide a reference to an object held in the session of the application.
	 * 
	 * @param name
	 *            name of the object
	 * @return the found object. If the name is NULL or empty then NULL will be
	 *         returned. If no object is found NULL will be returned.
	 */
	public static Object getSessionObject(String name) {
		if (name == null || name.trim().length() == 0) {
			return null;
		}

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> session = externalContext.getSessionMap();

		Object sessionObject = session.get(name);
		return sessionObject;
	}

	/**
	 * Provide the username of the user logged into the current session
	 * 
	 * @return the corresponding username. NULL if the name could not be found.
	 */
	public static String getLoggedUserName() {
		Object sessionObject = getSessionObject(BeanNames.SESSION_BEAN);
		if (sessionObject != null && sessionObject instanceof SessionBean) {
			SessionBean sessionBean = (SessionBean) sessionObject;
			String userName = sessionBean.getUserName();
			return userName;
		}

		return null;
	}
}
